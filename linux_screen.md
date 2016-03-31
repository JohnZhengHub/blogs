

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
    
