原文地址在文章末尾。

原文解释了几个关于 websocket 和 socket.io 的误区，并给出实验结果。实验结果同学们可以自己看，下面是作者指出的三个常见误区：

使用 sokcet.io 要比直接用 websocket 简单很多。
浏览器对 Websocket 的支持还不全面。
当老的浏览器不支持 Websocket 时，socket.io 会降级进行其他方式的连接。 实际情况不是这样的，socket.io 在初始连接的时候，是使用 AJAX 方式进行连接，在交换信息之后确定浏览器支持 Websocket 了，才升级到 Websocket 方式。

https://stackoverflow.com/a/38558531/3054511

