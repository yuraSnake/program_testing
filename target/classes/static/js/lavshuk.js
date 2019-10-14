$(document).ready(function() {
    //еще
    $("body").on("click", "#addSetting", function(event) {
        var object = $("#chapter_hard_test").find("div:last-child");
        var oldHtml = $(object).html();
        var oldId  = $(object).attr("id");
        var newId = parseInt(oldId);
        newId = newId + 1;
        var newDiv = "<div class='block_setting' id='" + String(newId) + "'>" + oldHtml + "</div>";
        $("#chapter_hard_test").append($(newDiv));
    });

    // question
    $("body").on("click", "#createQuestion", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/question",
            async : false,
            cache : false,
            data : "countAnswer=" + $("#countAnswer").val(),
            success : function(data) {
                $("body").html(data);
            }
        });
    });

    $("body").on("click", "#cleanAllQuestion", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/cleanAllQuestion",
            async : false,
            cache : false,
            success : function(data) {
                location.reload();
                alert("все вопросы успешно очищены");
            }
        });
    });

    $("body").on("click", "#deleteQuestionFromArrayQuestion", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/deleteQuestionFromArrayQuestion",
            async : false,
            cache : false,
            data : "nameOfQuestion=" + $("#listQuestion").val(),
            success : function(data) {

            }
        });

        $.ajax({
            method : "POST",
            url : "/lavshuk/back",
            async : false,
            cache : false,
            //data :
            success : function(data) {
                $("body").html(data);
                location.reload();
            }
        });
    });

    $("body").on("click", "#showQuestionInformation", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/showInformationQuestion",
            async : false,
            cache : false,
            data : "nameOfQuestion=" + $("#listQuestion").val(),
            success : function(data) {
                $("body").html(data);
            }
        });

    });

    $("body").on("click", "#sendArrayQuestion", function(event) {
        var length = $('.A').length;
        var array = [];
        var i;
        for(i = 0; i < length; i++) {
            array.push($('#' + (i + 1)).val());
        }

        var object = {};
        object.name = $("#question").val();
        if($("#listTheme").val() == "no theme")
            object.theme = null;
        else
            object.theme = $("#listTheme").val();

        object.answers = [];
        for(i = 0; i < length; i++) {
            var answer = {};
            answer.name = array[i];
            if($("input[name='right']:checked").val() == i+1)
                answer.right = true;
            else
                answer.right = false;
            object.answers.push(answer);
        }

        $.ajax({
            method : "POST",
            url : "/lavshuk/insertToArrayQuestion",
            dataType : "json",
            contentType : "application/json",
            async : false,
            cache : false,
            data : JSON.stringify(object),
            success : function(data) {

            }
        });

        $('#questionBack').click();

    });

    $("body").on("click", "#saveArrayOfQuestionsToFile", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/saveArrayOfQuestionsToFile",
            async : false,
            cache : false,
            success : function(data) {

            }
        });
    });

    // simple test
    $("body").on("click", "#sendArrayTest", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/sendArrayBlank",
            async : false,
            cache : false,
            data : "numberOfTest=" + $("#numberOfTest").val() + "&" + "countQuestion=" + $("#countQuestion").val(),
            success : function(data) {
                alert("тест успешно добавлен");
            }
        });

    });

    $("body").on("click", "#cleanAllTest", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/cleanAllBlank",
            async : false,
            cache : false,
            success : function(data) {
                alert("все тесты успешно очищены");
            }
        });
    });

    $("body").on("click", "#createFileOfTest", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/makeRezultTest",
            async : false,
            cache : false,
            success : function(data) {

            }
        });
    });

    $("body").on("click", "#createFileOfAnswersOnTest", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/makeRezultAnswersOfTests",
            async : false,
            cache : false,
            success : function(data) {

            }
        });
    });



    $("body").on("click", "#createTest", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/test",
            async : false,
            cache : false,
            success : function(data) {
                $("body").html(data);
            }
        });

    });

    $("body").on("click", "#testBack", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/back",
            async : false,
            cache : false,
            //data :
            success : function(data) {
                $("body").html(data);
                location.reload();
            }
        });
    });

    $("body").on("click", "#questionBack", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/back",
            async : false,
            cache : false,
            //data :
            success : function(data) {
                $("body").html(data);
                location.reload();
            }
        });
    });

    // theme
    $("body").on("click", "#createTheme", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/theme",
            async : false,
            cache : false,
            success : function(data) {
                $("body").html(data);
            }
        });
    });

    $("body").on("click", "#themeBack", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/back",
            async : false,
            cache : false,
            //data :
            success : function(data) {
                $("body").html(data);
                location.reload();
            }
        });
    });

    $("body").on("click", "#sendArrayTheme", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/sendArrayTheme",
            async : false,
            cache : false,
            data : "nameOfTheme=" + $("#nameOfTheme").val(),
            success : function(data) {

            }
        });

        $('#themeBack').click();

    });

    //hard test
    $("body").on("click", "#createTestWithTheme", function(event) {
        $.ajax({
            method : "POST",
            url : "/lavshuk/hardTest",
            async : false,
            cache : false,
            success : function(data) {
                $("body").html(data);
            }
        });

    });

    $("body").on("click", "#hardTestSendInformation", function(event) {
        var arrayData = [];
        var arrayDiv = $("#chapter_hard_test > div");
        $.each(arrayDiv, function(key, value) {
            var object = {};
            object.countQuestion = $(value).find("input").val();
            object.nameTheme = $(value).find("select").val();
            arrayData.push(object);
        });

        var hardTest = {};
        hardTest.number = $("#numberOfTestHardTest").val();
        hardTest.listInformation = arrayData;

        $.ajax({
            method : "POST",
            url : "/lavshuk/sendArrayBlankHardTest",
            dataType : "json",
            contentType : "application/json",
            cache : false,
            data : JSON.stringify(hardTest),
            success : function() {
                alert("комбинированный тест успешно добавлен");
            },
            error : function() {
                alert("комбинированный тест успешно добавлен :)");
            }
        });

        //$('#questionBack').click();

    });

});
