# module

编译时，需要让编译器能生成构造`XXXModule`对象的方法

这个有两种办法：

1. 一种是在`XXXModule`的构造函数声明`@inject`，告诉编译器需要`XXXModule`对象时从这里构造就可以
> 参考`GitHubApiModule`

2. 一种是在 XXXModule 里以 @Provide 的形式提供
> 参考`AppModule`

不然编译时会报错`cannot be provided without an @Inject constructor or from an @Provides- or @Produces-annotated method`