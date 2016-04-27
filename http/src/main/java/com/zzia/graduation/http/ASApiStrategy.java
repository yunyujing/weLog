package com.zzia.graduation.http;

/**
 * Created by apple on 15/7/10.
 * NET，CACHE，PERSETCACHE使用策略
 */
public enum ASApiStrategy {
    /**
     * 只使用NET
     */
    Net(ASApiDataFrom.NET),
    /**
     * 只使用CACHE
     */
    Cache(ASApiDataFrom.CACHE),
    /**
     * 只使用PERSETCACHE
     */
    PersetCache(ASApiDataFrom.PRESET_CACHE),
    /**
     * 使用CACHE和NET,
     * 先CACHE后NET,
     * 不依赖是否过期,不管怎样都适用网络
     * 可能返回二次
     */
    Cache_Net(ASApiDataFrom.CACHE, ASApiDataFrom.NET),
    /**
     * 使用CACHE和NET,
     * 先CACHE后NET,
     * 不使用旧的缓存,
     * 有未过期缓存,不使用NET
     * 返回一次
     */
    UnUseOutDataCache(ASApiDataFrom.CACHE, ASApiDataFrom.NET),
    /**
     * 使用CACHE和NET,
     * 先CACHE后NET,
     * 使用旧的缓存,
     * 有未过期缓存,不使用NET
     * 缓存获取，只从NET获取存储到缓存，但是不更新界面
     * 返回一次
     */
    UseOutDataFirstCache(ASApiDataFrom.CACHE, ASApiDataFrom.NET),
    /**
     * 使用CACHE和NET,
     * 先CACHE后NET,
     * 使用旧的缓存,
     * 有未过期缓存,不使用NET
     * 可能返回二次
     */
    UseOutDataCache(ASApiDataFrom.CACHE, ASApiDataFrom.NET),
    /**
     * 使用PERSETCACHE和CACHE和NET,
     * 先CACHE后PERSET后NET,
     * 使用旧的缓存,
     * 有未过期缓存,不使用NET
     * 没有CACHE才使用PERSETCACHE
     * 使用PERSETCACHE,要使用NET
     * 可能返回二次
     */
    Perset_Cache_Net(ASApiDataFrom.PRESET_CACHE, ASApiDataFrom.CACHE, ASApiDataFrom.NET);

    ASApiStrategy(ASApiDataFrom... dataFroms) {
        this.dataFroms = dataFroms;
    }

    ASApiDataFrom[] dataFroms;
}
