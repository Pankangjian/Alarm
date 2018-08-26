# Alarm
前段时间有客户有个需求，要在机顶盒上实现定时开关机，关机的话较为容易实现，但是开机的话需要底层硬件支持，与客户沟通后，定时休眠唤醒可符合客户需求，

此APP主要通过AlarmManager（闹钟服务）来实现

AlarmManager（闹钟服务）相关方法讲解：

set（int type，long startTime，PendingIntent pi）：一次性闹钟

setRepeating（int type，long startTime，long intervalTime，PendingIntent pi）：重复性闹钟，和3有区别，3闹钟间隔时间不固定

setInexactRepeating（int type，long startTime，long intervalTime，PendingIntent pi）：重复性闹钟，时间不固定

取消（PendingIntent pi）：取消AlarmManager的定时服务

getNextAlarmClock（）：得到下一个闹钟，返回值AlarmManager.AlarmClockInfo

setAndAllowWhileIdle（int type，long triggerAtMillis，PendingIntent operation）和set方法类似，这个闹钟运行在系统处于低电模式时有效

setExact（int type，long triggerAtMillis，PendingIntent operation）：在规定的时间精确的执行闹钟，比设置方法设置的精度更高

setTime（long millis）：设置系统墙上的时间

setTimeZone（String timeZone）：设置系统持续的默认时区

setWindow（int type，long windowStartMillis，long windowLengthMillis，PendingIntent operation）：设置一个闹钟在给定的时间窗触发。类似于set，该方法允许应用程序精确地控制操作系统调整闹钟触发时间的程度。

界面UI很简易，就两个按钮，一个用来定时休眠，一个用来定时唤醒，通过定时广播来发送指令唤醒
在进行测试时发现在Android4.4.2系统下时间唤醒无问题出现，但在Android7.0系统下定时无法唤醒，查阅了Android官方文档发现在Android4.4.2版本以上谷歌进行了电源优化，setRepeating方法不再适用，谷歌提供了其他的方法，如setExact方法，在进行大量的测试后，发现用了很多方法都无法准确唤醒，有时时差太长，有时无法唤醒，出现种种问题
最后通过原厂工程师指点添加一个AlertWakeLock的类来实现，通过休眠锁来唤醒，因为在机顶盒上是通过插电源来通电的，没有电池，所以可以不考虑电源电池功耗问题

wakelock锁介绍：
PowerManager.WakeLock有加锁与解锁两种状态，而加锁的形式有两种:

①永久锁住，这种锁除非显式的放开，否则是不会解锁的，所以用起来需要非常小心！

②超时锁，到时间后就会解锁，而创建WakeLock后，有两种加锁机制: ①不计数锁机制，②计数锁机制(默认) 可通过setReferenceCounted(boolean value)来指定,区别在于: 前者无论acquire( )多少次，一次release( )就可以解开锁。 而后者则需要(--count == 0)的时候，同样当(count == 0)才会去申请锁 所以，WakeLock的计数机制并不是正真意义上对每次请求进行申请/释放一个锁; 只是对同一把锁被申请/释放的次数来进行统计，然后再去操作！
