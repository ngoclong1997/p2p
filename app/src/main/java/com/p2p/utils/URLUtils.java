package com.p2p.utils;

public class URLUtils {
    public static final String BASE_URL = "http://66.42.51.238:8080/app/rest/";
    public static final String GET_OAUTH_URL = "v2/oauth/token";
    public static final String GET_CURRENT_USER_URL = "api/user/profile";
    public static final String REVOKE_URL = "v2/oauth/revoke";
    public static final String REGISTER_URL = "v2/services/p2p_UserService/register";
    public static final String GET_VALIDATION_CODE_URL = "v2/services/p2p_EmailService/sendVerificationCodeForEmail";
    public static final String RESET_PASSWORD_URL = "v2/services/p2p_UserService/resetPassword";
    public static final String CHANGE_PASSWORD_URL = "api/user/change-password";
    public static final String GET_COMMUNITY_URL = "v2/services/p2p_UserService/getCommunityInfor";
    public static final String GET_HISTORY_TRANSACTION_URL = "api/user/transaction/history";
    public static final String SEND_EMAIL_SUPPORT_URL = "api/email-confirmation/send-support-email";
    public static final String JAVIS_AI_URL = "v2/services/p2p_CoinService/getAll";
    public static final String TRANSFER_URL = "api/wallet/transaction";
    public static final String GET_QUOTATION_URL = "pricemulti?fsyms=BTC,ETH,EOS,TRX,LTC,QTUM,XRP,DASH,BCH,ZEC,NEO,ETC,XLM,BAT,USDC,ZRX&tsyms=USD&api_key=957ca8bd01d4c31dd24172b2fc159dc1f376a3f23281d5e8f61b9252cdb31bad";
    public static final String GET_NEWS_URL = "v2/news/?lang=EN";
    public static final String GET_APP_VERSION = "v2/services/p2p_AppVersionService/getByPlatform";
    public static final String GET_UNITS_URL = "v2/services/p2p_UnitService/getAll";
}
