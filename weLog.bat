:: echo命令——显示该命令并且输出hello
echo hello

::@命令——不显示该命令，直接输出hello
@echo hello

::echo off命令——显示echo off命令，不显示echo命令，输出hello
echo off
echo hello

::@echo off命令——命令都不显示，输出hello
@echo off
echo hello

::pause命令——需要用户点击任意键进行继续执行
pause
echo hello

::call命令——一个批处理程序调用另一个批处理程序，并且不会终止父批处理程序


::start命令——调用外部程序


::choice命令——可以让用户输入一个字符，从而运行不同的命令。使用时应该加/c：参数，c：后应写提示可输入的字符，之间无空格。它的返回码为1234···
choice /c:dme defrag,mem,end
if errorlevel 3 goto defrag
if errorlevel 2 goto mem
if errorlevel 1 goto end

:defrag
c:\dos\defrag
goto end
:mem
mem
goto end
:end
echo good bye

::if命令——根据判断的条件从而决定执行不同的命令，有三种格式：
::① if "参数"=="字符串" 待执行的命令
::② if exist文件名 待执行的命令
::③ if errorlevel/if not errorlevel 数字 待执行的命令


::for命令——用于参数在制定的范围内循环执行命令，指定变量要使用%%variable
::for{%%variable} in (set) do command[CommandLineOptions]
::其中，%%variable指定一个单一字母可替换的参数；(set)指定一个或一组文件，可以使用通配符；command指定对每个文件执行的命令；command—parameters为特定命令指定参数或命令行开关

::for /D %variable in (set) do command [command-parameters]
::如果集中包含通配符，则指定与目录名匹配，而不与文件名匹配

::for /R [[drive:]path] %variable in (set) do command [command-parameters]
::检查以[drive:]path为根的目录树，指向每个目录中的for语句如果在/r后没有指定目录，则使用当前目录。如果集仅为一个单点（.）字符，则枚举该目录树。

::for /L %variable in (start，step,end) do command [command-paramters]
::该集表示以增量形式从开始到结束的一个数字序列

::for /F ["options"] %variable in (file-set|"string"|'command') do command

::组合命令——1.&同时执行多条命令， 2.&&同时执行多条命令，当碰到执行出错的命令后将不执行后面的命令 3.||同时执行多条命令，当碰到执行正确的命令后将不执行后面的命令
::管道命令——1.|将第一条命令的结果作为第二条命令的参数来使用 2.>将一条命令或某个程序输出结果的重定向到特定文件中，会清除掉原有文件的内容后写入指定文件，>>将一条命令或某个程序输出结果的重定向到特定文件中，只会追加内容到指定文件中，而不会改动其中的内容
::         3.<从文件中而不是从键盘中读入命令输入， >&将一个句柄的输出写入到另一个句柄的输入中， <&从一个句柄读取输入并将其写入到另一个句柄输出中
