package com.example.repofitutils.model

import com.example.repofitutils.entity.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    //Mulit视频信息模块2
    @GET("/videomulti/getMutilVideoByTypeId")//根据视频分类获取对应的视频信息
    suspend fun getMutilVideoByTypeId(@Query("page")page:Int,@Query("pagesize")pagesize:Int,@Query("typeid")typeid:Int,):ReporfitData<List<IUnionVideoMutilEntity>>
    @GET("/videomulti/getRecommendMutilVideo")//获取推荐视频信息
    suspend fun getRecommendMutilVideo(@Query("page")page:Int,@Query("pagesize")pagesize:Int):ReporfitData<List<IUnionVideoMutilEntity>>

    //Simple视频模块2
    @GET("/videosimple/getRecommendSimpleVideo")//获取推荐Simple视频信息
    suspend fun getRecommendSimpleVideo(@Query("page")page:Int,@Query("pagesize")pagesize:Int):ReporfitData<List<IUnionVideoSimpleEntity>>
    @GET("/videosimple/getSimpleVideoByChannelId")//根据ChannelId获取对应Simple视频信息
    suspend fun getSimpleVideoByChannelId(@Query("channelId") channelId: String, @Query("page") page:Int, @Query("pagesize") pagesize:Int):ReporfitData<List<IUnionVideoSimpleEntity>>

    //WebSocket模块2
    @GET("/api/socket/index/{userId}")//socket
    suspend fun socketIndex(@Path("userId") userId:String): ModelAndView
    @POST("/api/socket/socket/push/{cid}")//推送数据接口
    suspend fun socketPush(@Path("cid")cid:String,@Query("message")message:String)

    //关注模块2
    @POST("/focuse/focuse")//关注  关注者id       被关注者id
    suspend fun focuse(@Query("focuseUserid")focuseUserid:Int,@Query("focusedUserid")focusedUserid:Int):ReporfitData<FocuseEntity>
    @DELETE("/focuse/unFocuse")//取消关注
    suspend fun unFocuse(@Query("focuseUserid")focuseUserid:Int,@Query("focusedUserid")focusedUserid:Int):ReporfitData<Boolean>

    //回复模块5
    @POST("/reply/agree")//评论点赞
    suspend fun replyAgree(@Query("replyid")replyid:Int):ReporfitData<Boolean>
    @GET("/reply/getReplyByCommentId")//根据评论id获取回复信息
    suspend fun getReplyByCommentId(@Query("commentid")commentid:Int):ReporfitData<List<ReplyEntity>>
    @DELETE("/reply/removeReply")//删除回复
    suspend fun removeReply(@Query("id")id:Int):ReporfitData<Boolean>
    @POST("/reply/reply")//回复评论
    suspend fun reply(@Body body: RequestBody):ReporfitData<ReplyEntity>
    @POST("/reply/unAgree")//取消点赞
    suspend fun unAgree(@Query("replyid")replyid:Int):ReporfitData<Boolean>

    //弹幕模块3
    @GET("/bulletscreen/getBulletScreenInfo")//获取弹幕信息
    suspend fun getBulletScreenInfo(@Query("datatype")datatype:Int,@Query("itemid")itemid:String):ReporfitData<List<BulletScreenEntity>>
    @POST("/bulletscreen/publishBulletScreen")//发布弹幕信息
    suspend fun publishBulletScreen(@Body body: RequestBody):ReporfitData<BulletScreenEntity>
    @DELETE("/bulletscreen/removeBulletScreen")//删除弹幕信息
    suspend fun removeBulletScreen(@Query("id")id:Int):ReporfitData<Boolean>

    //快手视频数据同步模块1
    @POST("/sync/sync")//同步快手视频数据
    suspend fun sync():ReporfitData<Boolean>

    //播放次数模块1
    @POST("/play/syncPlayNums")//同步播放次数
    suspend fun syncPlayNums(@Body body: RequestBody):ReporfitData<PlayEntity>

    //收藏模块2
    @POST("/collection/collection")//收藏
    suspend fun collection(@Body body: RequestBody):ReporfitData<CollectionEntity>
    @DELETE("/collection/unCollection")//取消收藏
    suspend fun unCollection(@Body body: RequestBody):ReporfitData<Boolean>

    //点赞模块2
    @PUT("/agree/agree")//点赞
    suspend fun agree(@Body body: RequestBody):ReporfitData<AgreeEntity>
    @DELETE("/agree/unagree")//取消点赞
    suspend fun unagree(@Body body: RequestBody):ReporfitData<Boolean>

    //用户模块4
    @GET("/user/getUsers")//获取所有用户
    suspend fun getUsers():ReporfitData<MutableList<UserEntity>>
    @FormUrlEncoded
    @POST("/user/login")//用户登录方法
    suspend fun login(@Field("username")username:String,@Field("password")password:String):ReporfitData<UserEntity>
    @PUT("/user/modifyPwd")//修改密码
    suspend fun modifyPwd(@Query("newPwd")newPwd:String,@Query("userid")userid:Int):ReporfitData<Boolean>
    @FormUrlEncoded
    @POST("/user/register")//用户注册方法
    suspend fun register(@Field("username")username:String,@Field("password")password:String):ReporfitData<UserEntity>

    //视频作者模块1
    @GET("/videoauthor/getAuthorById")//通过userid获取对应作者信息
    suspend fun getAuthorById(@Query("userid")userid:String):ReporfitData<VideoAuthorEntity>

    //视频类型模块3
    @GET("/videotype/getMutilType")//获取MutilType视频类型信息
    suspend fun getMutilType():ReporfitData<MutableList<VideoTypeEntity>>
    @GET("/videotype/getMutilTypeByPid")//根据上级id获取Mutil视频类型信息
    suspend fun getMutilTypeByPid(@Query("pid")pid:Int):ReporfitData<MutableList<VideoTypeEntity>>
    @GET("/videotype/getSimpleType")//获取Simple视频类型信息
    suspend fun getSimpleType():ReporfitData<List<VideoTypeEntity>>

    //评论模块5
    @POST("/comment/agree")//评论点赞
    suspend fun commentAgree(@Query("commentid")commentid:Int):ReporfitData<Boolean>
    @GET("/comment/getCommentByUserId")//获取评论信息
    suspend fun getCommentByUserId(@Query("datatype")datatype:Int,@Query("itemid")itemid:String):ReporfitData<List<CommentEntity>>
    //账户模块9
    //银行卡模块5





}