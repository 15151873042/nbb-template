package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.constant.SystemDictConstants;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.utils.SecurityUtils;
import com.nbb.template.system.core.utils.StrUtil;
import com.nbb.template.system.domain.entity.SysMenuDO;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.domain.vo.MenuTreeVO;
import com.nbb.template.system.domain.vo.MetaVO;
import com.nbb.template.system.domain.vo.RouterVO;
import com.nbb.template.system.mapper.SysMenuMapper;
import com.nbb.template.system.mapper.SysRoleMenuMapper;
import com.nbb.template.system.service.SysMenuService;
import com.nbb.template.system.service.SysUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 胡鹏
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuDO> implements SysMenuService {


    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysUserService sysUserService;

    @Override
    @Cacheable(cacheNames = CoreCacheConstants.ROLE_MENU_PERMS_KEY, key = "#roleId", unless = "#result == null")
    public Set<String> listPermsByRoleId(Long roleId) {
        MPJLambdaWrapper<SysRoleMenuDO> wrapper = new MPJLambdaWrapper<SysRoleMenuDO>()
                .innerJoin(SysMenuDO.class, SysMenuDO::getId, SysRoleMenuDO::getMenuId)
                .select(SysMenuDO::getId, SysMenuDO::getPerms)
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .distinct();

        List<SysMenuDO> menuList = sysRoleMenuMapper.selectJoinList(SysMenuDO.class, wrapper);
        return menuList.stream()
                .map(SysMenuDO::getPerms)
                .filter(StrUtil::isNotEmpty)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(cacheNames = CoreCacheConstants.ROLE_MENU_KEY, key = "#roleId", unless = "#result == null")
    public List<SysMenuDO> listMenuByRoleId(Long roleId) {
        MPJLambdaWrapper<SysRoleMenuDO> wrapper = new MPJLambdaWrapper<SysRoleMenuDO>()
                .innerJoin(SysMenuDO.class, SysMenuDO::getId, SysRoleMenuDO::getMenuId)
                .selectAll(SysMenuDO.class)
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .eq(SysMenuDO::getStatus, "0")
                .in(SysMenuDO::getMenuType, "M", "C")
                .distinct();

        return sysRoleMenuMapper.selectJoinList(SysMenuDO.class, wrapper);
    }

    @Override
    public List<MenuTreeVO> getMenuTreeByUserId(long userId) {
        List<SysMenuDO> menus;

        if (SecurityUtils.isAdmin(userId)) {
            menus = getBaseMapper().listMenuAll();
        } else {

            Set<Long> roleIds = sysUserService.listRoleIdById(userId);

            menus = roleIds.stream()
                    .flatMap(roleId -> {
                        return ((SysMenuService) AopContext.currentProxy()).listMenuByRoleId(roleId).stream();
                    })
                    // 多个角色含有相同的菜单时，需要对菜单进行去重
                    .collect(Collectors.toMap(
                            SysMenuDO::getId,
                            Function.identity(),
                            (existing, replacement) -> existing))
                    .values()
                    .stream()
                    .collect(Collectors.toList());
        }

        return getChildPerms(menus, 0);
    }

    @Override
    public List<RouterVO> buildMenus(List<MenuTreeVO> menus) {
        List<RouterVO> routers = new LinkedList<>();
        for (MenuTreeVO menu : menus) {
            RouterVO router = new RouterVO();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
            List<MenuTreeVO> cMenus = menu.getChildren();
            if (CollUtil.isNotEmpty(cMenus) && SystemDictConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVO> childrenList = new ArrayList<>();
                RouterVO children = new RouterVO();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(getRouteName(menu.getRouteName(), menu.getPath()));
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVO> childrenList = new ArrayList<RouterVO>();
                RouterVO children = new RouterVO();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent("InnerLink");
                children.setName(getRouteName(menu.getRouteName(), routerPath));
                children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<MenuTreeVO> getChildPerms(List<SysMenuDO> list, int parentId) {
        List<MenuTreeVO> menuTreeList = BeanUtil.copyToList(list, MenuTreeVO.class);

        List<MenuTreeVO> returnList = menuTreeList.stream()
                .filter(menu -> menu.getParentId() == parentId)
                .peek(menu -> recursionFn(menuTreeList, menu))
                .collect(Collectors.toList());

        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t    子节点
     */
    private void recursionFn(List<MenuTreeVO> list, MenuTreeVO t) {
        // 得到子节点列表
        List<MenuTreeVO> childList = getChildList(list, t);

        t.setChildren(childList);

        for (MenuTreeVO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<MenuTreeVO> getChildList(List<MenuTreeVO> list, MenuTreeVO t) {
        return list.stream()
                .filter(menu -> menu.getParentId().longValue() == t.getId().longValue())
                .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<MenuTreeVO> list, MenuTreeVO t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(MenuTreeVO menu) {
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            return StrUtil.EMPTY;
        }
        return getRouteName(menu.getRouteName(), menu.getPath());
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(MenuTreeVO menu) {
        return menu.getParentId().longValue() == 0L
                && SystemDictConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(SystemDictConstants.NO_FRAME);
    }

    /**
     * 获取路由名称，如没有配置路由名称则取路由地址
     *
     * @param name 路由名称
     * @param path 路由地址
     * @return 路由名称（驼峰格式）
     */
    public String getRouteName(String name, String path) {
        String routerName = StringUtils.isNotEmpty(name) ? name : path;
        return StrUtil.upperFirst(routerName);
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(MenuTreeVO menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && SystemDictConstants.TYPE_DIR.equals(menu.getMenuType())
                && SystemDictConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(MenuTreeVO menu) {
        return menu.getIsFrame().equals(SystemDictConstants.NO_FRAME) && StrUtil.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(MenuTreeVO menu) {
        return menu.getParentId().intValue() != 0 && SystemDictConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(MenuTreeVO menu) {
        String component = "Layout";
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = "InnerLink";
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = "ParentView";
        }
        return component;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    public String innerLinkReplaceEach(String path) {
        return path.replace("http://", "")
                .replace("https://", "")
                .replace("www.", "")
                .replace(".", "/")
                .replace(":", "/");
    }
}
