package com.example.qhsj.phototest;

import android.os.Environment;

/**
 * @author: star
 * @time: 2016/7/9 17:51
 * @description: 静态类 常量定义
 */
public class Constant {

    /**
     * 保存图片本地路径
     */
    public static final String KWALLET_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/PWallet/";
    public static final String KWALLET_IMAGE = "image/"; //图片显示文件夹
    public static final String KWALLET_IMAGE_CACHE = "image_cache/";//图片缓存
    public static final String KWALLET_IMAGE_HEAD = "head/";

    public static final String IMG_PATH = KWALLET_DIR + KWALLET_IMAGE;
    public static final String IMG_CACHE_PATH = KWALLET_DIR + KWALLET_IMAGE_CACHE;
    public static final String IMAGE_HEAD_PATH = KWALLET_DIR + KWALLET_IMAGE_HEAD;

    public static final int TAKE_A_PICTURE = 10;//拍照
    public static final int SELECT_A_PICTURE = 11; //裁剪相册图片方法实现
    public static final int SET_PICTURE = 12; //裁剪相机图片方法实现
    public static final int SET_ALBUM_PICTURE_KITKAT = 13; //4.4及以上改动版裁剪相机图片方法实现
    public static final int SELECT_A_PICTURE_AFTER_KITKAT = 14;//4.4及以上相册裁剪图片方法实现


    /**
     * 静态常量
     */
    public static final int GET_NET_DATA = 0x05; //获取网络数据
    public static final int UPDATE_LIST_REFRESH = 0x06; // 刷新
    public static final int UPDATE_LIST_NEXT_PAGE = 0x07; // 下一页

    public static final int WEI_BO_DETAIL_FORWARDING_REFRESH = 0x08;  //微博详情转发刷新
    public static final int WEI_BO_DETAIL_FORWARDING_NEXT_PAGE = 0x09;//微博详情转发加载
    public static final int WEI_BO_DETAIL_COMMENT_REFRESH = 0x010;//微博详情评论刷新
    public static final int WEI_BO_DETAIL_COMMENT_NEXT_PAGE = 0x11;//微博详情评论加载
    public static final int COMMENT_LIST_TO_REPLY = 0x12;//评论列表进入回复
    public static final int WEI_BO_DETAIL_TO_FORWARDING = 0x13;//详情进入转发
    public static final int WEI_BO_DETAIL_TO_COMMENT = 0x14;//详情进入评论
    public static final int WEI_BO_MESSAGE_TO_MAIN = 0x15;//消息界面进入微博主界面
    public static final int WEI_BO_MAIN_TO_DETAIL_COMMENT_LIST = 0x16;//微博主界面进入详情评论列表
    public static final int WEI_BO_COMMENT_MESSAGE_TO_DETAIL_COMMENT_LIST = 0x17;//微博消息评论进入详情评论列表

    public static final int CREATE_DEPOSIT_ORDER_TO_CHOOSE_BOSS = 20;//创建充值订单进入选老板
    public static final int CREATE_DRAW_ORDER_TO_CHOOSE_BOSS = 21;//创建提现订单进入选老板

    public static final int ORDER_DETAIL_TO_CHOOSE_BOSS = 22;//充值详情进入选老板

    public static final int EXCHANGE_FRAGEMNT_TO_ORDERUNFINISHED = 23;//充值详情进入选老板

    public static final int DETAIL_ORDER_TO_APPEAL = 31;//进入仲裁
    public static final int CANCEL_ORDER = 32;//取消订单
    public static final int CONFIRM_ORDER = 33;//确认订单
    public static final int CHOOSE_BOSS = 34;//选老板
    public static final int CANCEL_APPEAL = 35;//取消仲裁
    public static final int REFUSED_ORDER = 36;//拒绝接单

    public static final int SHARE_DEPOSIT_ORDER = 40;//共享充值订单
    public static final int SHARE_DRAW_ORDER = 41;//共享提现订单
    public static final int SHARE_DEPOSIT_FINISH_ORDER = 42;//共享充值完成订单
    public static final int SHARE_DRAW_FINISH_ORDER = 43;//共享提现完成订单

    public static final int FIND_TREASURE_AMAP = 50; //寻宝高德界面
    public static final int TREASURE_AMAP_DETAIL_UNLOCKE = 51; //藏宝图详情进入已解锁
    public static final int FIND_TREASURE_GOOGLE = 52; //寻宝高德界面
    public static final int NOTIFICATION_TO_FRIENDS = 53; //通知栏跳朋友
    public static final int TREASURE_DETAIL_MAP_TO_LIST = 54;//藏宝图详情到列表
    public static final int TREASURE_MAP_DETAIL_TO_PAY = 55;   //藏宝图详情跳转支付界面
    public static final int HEROPOST_WAIT_TO_CONFIRM = 56;   //英雄帖待确认
    public static final int UNEXPLORE_CONFIRM_ = 57;   //未探索确认完成任务
    public static final int UNSEND_LIST = 58;   //未发出
    public static final int UNEXPLORE_LIST = 59;   //未探索
    public static final int EXPLORE_LIST = 60;   //已探索

    public static final int HEROPOST_GET_LIST = 61;   //已领取

    public static final int USE_HERO_TO_ALREADY_LIST = 62;   //使用英雄帖进入已领取列表

    public static final int REFRESH_AR_UNFINISH_EXPLORE_LIST = 63; //刷新ar未完成探索列表

    public static final int REFRESH_AR_FINISH_EXPLORE_LIST = 64; //刷新ar已完成探索列表

    public static final int ADVERTISING_FINISH = 65; //广告

    /**
     * 时间格式
     */
    public static final String DATE_FORMAT = "MM-dd yyyy HH:mm";

    public static final String DATA_FORMAT_2 = "yyyy-MM-dd HH:mm";

    public static final String ACTION_TURN_FRIENDS = "ACTION_TURN_FRIENDS";

    public static final int QUESTION_UNSOLVED = 66;//问题反馈待解决
    public static final int QUESTION_SOLVED = 67;//问题反馈已解决
    public static final int QUESTION_UNSOLVEDS = 68;//问题反馈待解决

    public static final int UPDATE_SHOPPING_CART = 69;//更新购物车数据
    public static final int ORDER_ONLINE = 70;//线上订单
    public static final int ORDER_OFFLINE = 71;//线下订单

    public static final int SELL_RECORD_WAIT_FINISH = 72;//卖出记录-待完成
    public static final int SELL_RECORD_ALREADY_FINISH = 73;//卖出记录-已完成


}
