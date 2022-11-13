String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

// 답변하기를 클릭하면 addAnswer 함수를 실행
$(".answerWrite input[type=submit]").click(addAnswer);

// submit 버튼의 기본 동작을 막고, 사용자가 입력한 데이터를 JSON으로 변환
function addAnswer(e) {
    e.preventDefault(); //submit이 자동으로 동작하는 것을 막음.
    //form 데이터들을 자동으로 묶어줌
    var queryString = $("form[name=answer]").serialize();

    $.ajax({
        type : 'post',
        url : '/api/qna/addAnswer',
        data : queryString,
        dataType : 'json',
        //error : onError,
        success : onSuccess,
    });
}

// HTML템플릿과 이 템플릿에 값을 전달하는 template() 함수를 구현해 뒀기 때문에
// 아래와 같이 간단하게 추가가 가능함
function onSuccess(json, status) {
    var answerTemplate = $("#answerTemplate").html();

    console.log(json.answerId);

    var template = answerTemplate.format(json.answer.writer, new Date(json.answer.createdDate),
                                            json.answer.contents, json.answer.answerId, json.answer.answerId);
    $(".qna-comment-slipp-articles").prepend(template);
}

// 답변 삭제하기
$(".qna-comment").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var queryString = deleteBtn.closest("form").serialize();

    $.ajax({
        type : 'post',
        url : '/api/qna/deleteAnswer',
        data : queryString,
        dataType : 'json',
        error : function (xhr, stauts) {
            alert("error");
        },
        success : function (json, status) {
            if (json.status) {
                deleteBtn.closest('article').remove();
            }
        },
    });
}