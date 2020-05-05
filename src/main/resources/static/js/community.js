/**
 * Created by codedrinker on 2020/5/5.
 */

/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var commentId = $("#comment_id").val();
    var type = $("#type_id").val();
    var content = $("#comment_content").val();
    var title= $("#toName").text();
     if(type==1){
         comment2target(questionId, type,"评论:   "+content);
     }
     if (type==2){
         comment2target(commentId, type, "回复"+title+":   "+content);
     }
    return true;
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    if (!type) {
        alert("没有选择type回复对象~~~");
        return;
    } if (!targetId) {
        alert("没有选择targetId回复对象~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        //   window.open("https://github.com/login/oauth/authorize?client_id=88048ff3801fcb6f267e&redirect_uri=http://lcqjoyce.cn/callback&scope=user&state=1");
                        //   window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
    return true;
}

