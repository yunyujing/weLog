# 项目和代码说明——公司日志APP


## 项目实现的功能
1.员工新建、编辑、删除任务，上传该任务相关文件（主要是文字和图片）
2.管理阶层对任务的评价
3.制定工作计划
4.汇报工作总结
5.附加功能：实现员工的上下班打卡，加班认定，申请休假，差旅报销


## 项目开发注意事项
### 开发版本
1.Android studio 2.0
2.jdk的版本：1.7.0_79
3.sdk的版本：23（最小版本15）
4.gradle的版本：2.0.0
### 主要控件
1.主界面使用DrawerLayout嵌套CoordinatorLayout实现侧滑和悬浮按钮
2.二级界面使用AppBarLayout嵌套CollapsingToolbarLayout，再加上ToolBar实现滑动渐变的标题栏
3.列表的加载和刷新使用RecycleView和Refresh
### 第三方资源的引用
1.使用EventBus实现消息传递
2.使用Fresco实现图片加载和显示
3.使用retrofit实现网络请求