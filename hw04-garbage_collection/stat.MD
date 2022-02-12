До оптимизации:
Дефолтное значение 256m (G1GC): spend msec:17201, sec:17
heep 2gb: spend msec:16409, sec:16
heep 12gb: spend msec:16745, sec:16

После оптимизации:
G1GC 256m - spend msec:7708, sec:7, 2g - spend msec:3921, sec:3, 16g - spend msec:3254, sec:3
ParallelGC 256m - spend msec:15874, sec:15, 2g - spend msec:2472, sec:2 , 16g - spend msec:2463, sec:2
SerialGC 256m - spend msec:4569, sec:4 , 2g - spend msec:2512, sec:2, 16g - spend msec:2477, sec:2
ZGC 256m - spend msec:3592, sec:3 2g - spend msec:2374, sec:2 , 16g - spend msec:2527, sec:2
