1.题目及数据问题说明
    题目问题：
        题目中表述3个level的销售员分别得到1%,2.25%,3.5%的commission，但是所给样例中只有销售额在1000以上的才有提成，且提成比率都是1%
        因此按两种方式分别写了commission的计算方案，在CommissionCalculator.java的第55行开始。可自行选择一种方案。

    数据问题：
        发现所给的salesslips.txt中 4号Sharp, F（4条）和16号Pinz, R（5条）数据相比于样例有缺失，样例结果中应该分别有5条和7条。

2.代码解释
    主函数main中定义好各文件路径（可根据实际情况自行修改），然后进行employee、salesslip的读取解析，再进行数据整合 这三个步骤。
    processEmployeeFile  ： 读取employee时对每个销售员创建一个Salesman对象（可查看Salesman.java）；
    processSalesFile  ： 读取salesslip时把每条销售记录整合到销售员中去；
    calculateTable   ：  整合数据及输出
        整合数据主要工作：计算各销售员comm、bonus、salary，再整合各个组的数据
        输出时格式化：第一是每个数保留小数点两位，第二是每列保证字符长度一致且左对齐。
        各列的宽度可以通过更改117行columnLength数组的内容来自由更改。
