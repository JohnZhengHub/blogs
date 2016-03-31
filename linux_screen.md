
## 创建会话窗口 
    
    screen -S 命令
    
## 查看目前的窗口情况

     screen -ls

## 恢复会话窗口

     screen -r 12865

## 关闭某个会话

     screen -S  会话ID  -X quit 
    
## 关闭所有会话

    str=$(screen -ls)  
    array=$(echo $str|tr "." "\n")  
    for V in $array  
    do  
    if [ $V -gt 0  ]  
        then screen -S $V -X quit  
    fi  
    done  
    
