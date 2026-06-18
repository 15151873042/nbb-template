package com.nbb.template.system.core.constant;

/**
 * @author 胡鹏
 */
public class CoreCacheConstants {

    /**
     * 用户所拥有的角色ID redis key
     */
    public static final String USER_ROLE_ID_KEY = "user_id_2_role_ids";

    /**
     * 用户所拥有的角色key redis key
     */
    public static final String USER_ROLE_KEY_KEY = "user_id_2_role_keys";

    /**
     * 角色所拥有的菜单key redis key
     */
    public static final String ROLE_MENU_PERMS_KEY = "role_id_2_menu_perms";

    /**
     * 角色所拥有的菜单ID redis key
     */
    public static final String ROLE_MENU_ID_KEY = "role_id_2_menu_ids";




    /**
     * 角色所拥有的菜单 redis key
     */
    public static final String MENUS_KEY = "menus";



    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
}
