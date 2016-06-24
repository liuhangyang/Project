function addCheckAll(eid){
    var part = document.getElementById(eid);

    checkboxs = getEleByAtr(part,"input","type","checkbox");
    checkboxs[0].onclick=function(){
        if(this.checked){
            for (var i = 1; i<checkboxs.length; i++) {
                checkboxs[i].checked = true;
            }
        }else{
            for (var i = 1; i<checkboxs.length; i++) {
                checkboxs[i].checked = false;
            }
        }
    }
}

function getEleByAtr(obj,tagname,atrname,atrv){
    var olst = obj.getElementsByTagName(tagname);
    var res = [];
    for (var i = 0; i < olst.length; i++) {
        if(olst[i].getAttribute(atrname) == atrv){
            res.push(olst[i]);
        }
    }
    return res;
}

function getChecked(eid){
    var part = document.getElementById(eid);
    res = [];
    checkboxs = getEleByAtr(part,"input","type","checkbox");

    for (var i = checkboxs.length - 1; i > 0; i--) { //第一个选择框不用获取
        if(checkboxs[i].checked == true){
            res.push(checkboxs[i]);
        }
    }

    return res;
}


