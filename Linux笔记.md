
## 命令
### nohup
 - **描述**：nohup就是不挂起的意思( no hang up)。
 - **语法**：nohup Command [ Arg ... ] [　& ] 　　
 - **作用**：不挂断地运行命令

### sh
sh或是执行脚本，或是切换到sh这个bash里，默认的shell是bash

### source
 - **功能**：使Shell读入指定的Shell程序文件并依次执行文件中的所有语句source命令通常用于重新执行刚修改的初始化文件，使之立即生效，而不必注销并重新登录。
 - **用法**：source filename
 - **比较**：
source filename 与 sh filename 及./filename执行脚本的区别在那里呢？

1.当shell脚本具有可执行权限时，用sh filename与./filename执行脚本是没有区别得。./filename是因为当前目录没有在PATH中，所有"."是用来表示当前目录的。

2.sh filename 重新建立一个子shell，在子shell中执行脚本里面的语句，该子shell继承父shell的环境变量，但子shell新建的、改变的变量不会被带回父shell，除非使用export。

3.source filename：这个命令其实只是简单地读取脚本里面的语句依次在当前shell里面执行，没有建立新的子shell。那么脚本里面所有新建、改变变量的语句都会保存在当前shell里面。
 - **参考地址**：http://blog.csdn.net/wangyangkobe/article/details/6595143


