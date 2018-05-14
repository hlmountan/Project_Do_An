package com.paditech.mvpbase.common.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hung on 5/14/2018.
 */

class InfoBean {
    /**
     * id : 76
     * appid : in.amazon.mShop.android.shopping
     * is_file : 0
     * del_file : 0
     * price : 0
     * drop_percent : 0
     * drop_value : 0
     * endsale : 0
     * releasedate : 0
     * app_404 : 0
     * updated : 1526068671
     * status : 1
     * d_rating : 2500
     * rate_total : 2751570
     * title : Amazon India Online Shopping
     * urltitle : amazon-india-online-shopping
     * offerby : Amazon Mobile LLC
     * dev_id : Amazon+Mobile+LLC
     * tag : amazon, shop, shopping
     * category : Shopping
     * game : 0
     * cover : https://lh5.ggpht.com/TpxeOgptQworHjromT4yoo05oHk72dSE0pVQowPfb3u3rozTwLjYUHxTpEzIYMemomQ
     * check_price : 1481709180
     * video :
     * score : 4.3
     * update_app : 1524182400
     * size : 0
     * in_apps : 0
     * contains_ads : 0
     * installs : 100000000
     * version : 16.7.0.300
     * description : Amazon Online Shopping App  The Amazon India Shopping App brings to you, over 10 Crore original products and at great prices. Shop on the Amazon App for the latest electronics – Redmi 4, Apple iPhone 7, Samsung J7, OnePlus 3T & many more, accessories & software for your gadgets - memory cards, earphones, chargers, power banks & anti-virus. Don’t just stop there, shop for your home needs and choose from selection across TVs, refrigerators, water purifiers, washing machines, sofa sets, mattresses, bean bags, wall stickers & cookware. Shop from Amazon Fashion for all occasions – Formal attire, vacation wear to casual and daily wear – shirts for men, sarees & gowns for women, watches and jewellery to match and shoes & footwear to boot. Also, the kids range of clothes, footwear, toys - from remote control cars, to bicycles, skateboards and school supplies –bags, bottles & more. Download the Amazon India Shopping App for free, from Google Play Store & enjoy online shopping like never before.   Great Shopping Experience  The free online shopping app from Amazon assures you of a great shopping experience with a lighter app, faster load time & wide selection across categories. You can browse and search for your desired products by product name, category or brands. Enjoy the rich catalogue images - you can view the product from multiple angles, with options to zoom. Get personalised recommendations based on ratings & shopping trends. You can also share product links with your family & friends via email, SMS, WhatsApp, Facebook, Twitter, & more. With Amazon India, you are guaranteed 24x7 customer service support, faster delivery, easy returns, convenient payment options including cash on delivery as well as the 100% purchase protection offered by Amazon’s A-to-Z Guarantee.  Enjoy Safe and Secure Shopping  Shop using your Credit/Debit Card, EMI or Net Banking knowing that all transactions are securely processed. No need to worry about your personal or account details getting leaked, as Amazon.in provides you 100% purchase protection for safe payments & transactions. You could also pay cash on delivery for your orders.  Track your Orders  Shopping on the Amazon app offers you up to the minute updates on the status of your orders & you can choose to track it anytime through the ‘Your Orders’ menu option.  Shopping is Fun on the Amazon.in App  One of the best parts about shopping on the Amazon App are the exclusive App events. Get deals & discounts only on the app. App only games and quizzes games offer you the chance to win exciting prizes!  Make the most of Amazon’s exclusive benefits -   Become an Amazon Prime member & get guaranteed Free 1-Day, 2-Day or standard delivery on eligible items, 30 minute early access to top Lightning deals & watch the latest and exclusive movies and TV shows on Prime Video.  Use Amazon Pay for Instant checkout for your purchases & hassle-free payments, balance tracking to monitor your spends & view your balance at any given time & faster refunds should you require it.   Explore Amazon Exclusives for branded electronics, accessories, appliances, jewellery, fashion & more only available on Amazon.   With Amazon Pantry get next day delivery of household items & groceries and save in the process. Now available in 25 cities in India.  Amazon Global Store provides access to products sold by Amazon across the Globe. International brands & products from Fashion, Electronics, Home & Kitchen, Books & more at your fingertips.  Amazon Launchpad makes available to you, unique & innovative products from today’s brightest startups.  Permissions  In order to offer you the best experience and operate properly, the Amazon Shopping App needs access to the following services: * Operating System Requirements: Requires Android OS 2.3 or higher. * Account: Account permissions are needed for integration with Facebook & other social media network you have connected to your device to allow you to share products with family & friends.
     * requireandroid :
     * contentrating : Everyone
     * all_price :
     * uploaddate : 1522814400
     * commentcount : 0
     * ios : 0
     * amazon :
     */

    @SerializedName("id")
    private int id;
    @SerializedName("appid")
    private String appid;
    @SerializedName("is_file")
    private int isFile;
    @SerializedName("del_file")
    private int delFile;
    @SerializedName("price")
    private int price;
    @SerializedName("drop_percent")
    private int dropPercent;
    @SerializedName("drop_value")
    private int dropValue;
    @SerializedName("endsale")
    private int endsale;
    @SerializedName("releasedate")
    private int releasedate;
    @SerializedName("app_404")
    private int app404;
    @SerializedName("updated")
    private int updated;
    @SerializedName("status")
    private int status;
    @SerializedName("d_rating")
    private int dRating;
    @SerializedName("rate_total")
    private int rateTotal;
    @SerializedName("title")
    private String title;
    @SerializedName("urltitle")
    private String urltitle;
    @SerializedName("offerby")
    private String offerby;
    @SerializedName("dev_id")
    private String devId;
    @SerializedName("tag")
    private String tag;
    @SerializedName("category")
    private String category;
    @SerializedName("game")
    private int game;
    @SerializedName("cover")
    private String cover;
    @SerializedName("check_price")
    private int checkPrice;
    @SerializedName("video")
    private String video;
    @SerializedName("score")
    private String score;
    @SerializedName("update_app")
    private int updateApp;
    @SerializedName("size")
    private int size;
    @SerializedName("in_apps")
    private int inApps;
    @SerializedName("contains_ads")
    private int containsAds;
    @SerializedName("installs")
    private String installs;
    @SerializedName("version")
    private String version;
    @SerializedName("description")
    private String description;
    @SerializedName("requireandroid")
    private String requireandroid;
    @SerializedName("contentrating")
    private String contentrating;
    @SerializedName("all_price")
    private String allPrice;
    @SerializedName("uploaddate")
    private int uploaddate;
    @SerializedName("commentcount")
    private int commentcount;
    @SerializedName("ios")
    private int ios;
    @SerializedName("amazon")
    private String amazon;

    public static InfoBean objectFromData(String str) {

        return new Gson().fromJson(str, InfoBean.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public int getIsFile() {
        return isFile;
    }

    public void setIsFile(int isFile) {
        this.isFile = isFile;
    }

    public int getDelFile() {
        return delFile;
    }

    public void setDelFile(int delFile) {
        this.delFile = delFile;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDropPercent() {
        return dropPercent;
    }

    public void setDropPercent(int dropPercent) {
        this.dropPercent = dropPercent;
    }

    public int getDropValue() {
        return dropValue;
    }

    public void setDropValue(int dropValue) {
        this.dropValue = dropValue;
    }

    public int getEndsale() {
        return endsale;
    }

    public void setEndsale(int endsale) {
        this.endsale = endsale;
    }

    public int getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(int releasedate) {
        this.releasedate = releasedate;
    }

    public int getApp404() {
        return app404;
    }

    public void setApp404(int app404) {
        this.app404 = app404;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDRating() {
        return dRating;
    }

    public void setDRating(int dRating) {
        this.dRating = dRating;
    }

    public int getRateTotal() {
        return rateTotal;
    }

    public void setRateTotal(int rateTotal) {
        this.rateTotal = rateTotal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrltitle() {
        return urltitle;
    }

    public void setUrltitle(String urltitle) {
        this.urltitle = urltitle;
    }

    public String getOfferby() {
        return offerby;
    }

    public void setOfferby(String offerby) {
        this.offerby = offerby;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(int checkPrice) {
        this.checkPrice = checkPrice;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getUpdateApp() {
        return updateApp;
    }

    public void setUpdateApp(int updateApp) {
        this.updateApp = updateApp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getInApps() {
        return inApps;
    }

    public void setInApps(int inApps) {
        this.inApps = inApps;
    }

    public int getContainsAds() {
        return containsAds;
    }

    public void setContainsAds(int containsAds) {
        this.containsAds = containsAds;
    }

    public String getInstalls() {
        return installs;
    }

    public void setInstalls(String installs) {
        this.installs = installs;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequireandroid() {
        return requireandroid;
    }

    public void setRequireandroid(String requireandroid) {
        this.requireandroid = requireandroid;
    }

    public String getContentrating() {
        return contentrating;
    }

    public void setContentrating(String contentrating) {
        this.contentrating = contentrating;
    }

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public int getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(int uploaddate) {
        this.uploaddate = uploaddate;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public int getIos() {
        return ios;
    }

    public void setIos(int ios) {
        this.ios = ios;
    }

    public String getAmazon() {
        return amazon;
    }

    public void setAmazon(String amazon) {
        this.amazon = amazon;
    }
}
