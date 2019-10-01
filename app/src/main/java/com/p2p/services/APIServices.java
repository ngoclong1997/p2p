package com.p2p.services;

import com.google.gson.JsonObject;
import com.p2p.models.ChangePasswordObject;
import com.p2p.models.Community;
import com.p2p.models.HistoryTransaction;
import com.p2p.models.Javis;
import com.p2p.models.ListNews;
import com.p2p.models.ListUnit;
import com.p2p.models.User;
import com.p2p.utils.APIUtils;
import com.p2p.utils.URLUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIServices {

    @FormUrlEncoded
    @Headers({"Authorization: " + APIUtils.BASIC_AUTHORIZATION})
    @POST(URLUtils.GET_OAUTH_URL)
    Call<JsonObject> getOAuthToken(@Field("grant_type") String type, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @Headers({"Authorization: " + APIUtils.BASIC_AUTHORIZATION})
    @POST(URLUtils.REVOKE_URL)
    Call<Void> revokeToken(@Field("token") String token);

    @Headers({"Content-Type: application/json"})
    @GET(URLUtils.GET_CURRENT_USER_URL)
    Call<User> getCurrentUser(@Header("Authorization") String token);


    @Headers({"Content-Type: application/json"})
    @GET(URLUtils.GET_QUOTATION_URL)
    Call<JsonObject> getListQuotation();

    @Headers({"Content-Type: application/json"})
    @GET(URLUtils.GET_NEWS_URL)
    Call<ListNews> getListNews();

    @Headers({"Content-Type: application/json"})
    @GET(URLUtils.JAVIS_AI_URL)
    Call<Javis> getJavis();

    @POST(URLUtils.CHANGE_PASSWORD_URL)
    Call<String> changePassword(@Header("Authorization") String token, @Body ChangePasswordObject body);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.GET_COMMUNITY_URL)
    Call<Community> getCommunityInfo(@Header("Authorization") String token, @Body HashMap<String, String> body);

    @POST(URLUtils.SEND_EMAIL_SUPPORT_URL)
    Call<String> sendSupportEmail(@Header("Authorization") String token, @Body HashMap<String, String> body);

    @Headers({"Content-Type: application/json"})
    @GET(URLUtils.GET_HISTORY_TRANSACTION_URL)
    Call<List<HistoryTransaction>> getHistoricalTransaction(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.TRANSFER_URL)
    Call<JsonObject> sendProfit(@Header("Authorization") String token, @Body HashMap<String, String> body);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.TRANSFER_URL)
    Call<JsonObject> sendSystem(@Header("Authorization") String token, @Body HashMap<String, String> body);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.TRANSFER_URL)
    Call<JsonObject> sendTotal(@Header("Authorization") String token, @Body HashMap<String, String> body);

    @POST(URLUtils.REGISTER_URL)
    Call<String> register(@Body JsonObject body);


    @POST(URLUtils.GET_VALIDATION_CODE_URL)
    Call<Void> sendValidationEmail(@Body JsonObject body);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.RESET_PASSWORD_URL)
    Call<JsonObject> resetPassword(@Body JsonObject body);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.GET_APP_VERSION)
    Call<JsonObject> getAppVersion(@Body JsonObject body);

    @Headers({"Content-Type: application/json"})
    @POST(URLUtils.GET_UNITS_URL)
    Call<ListUnit> getAllUnits();


}
