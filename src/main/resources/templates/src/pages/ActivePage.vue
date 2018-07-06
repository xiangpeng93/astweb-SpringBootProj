<!--suppress ALL -->
<template>
    <main-layout>
        <div class="container" style="padding-bottom:10px;">
            <h2 class="text-center" style="word-break: break-all; word-wrap: break-word;">{{ head }}</h2>
            <br>
            <p style=" font-family: Helvetica;">
                创建时间:{{ time }} 阅读次数:{{ count }}
                <span class="pull-right">发布者:{{ auther }}</span>
                <br>
            </p>
            <hr>
            <div style="white-space: pre-wrap;word-wrap: break-word;">
                <span v-html="body"></span>
            </div>
            <div class="text-right">
                <button class="btn btn-primary" style="margin:5px;" @click="ActiveRegister()">报名请点击</button>
            </div>

            <!-- Modal -->
            <div class="container">
                <div class="modal fade" id="active_modal" tabindex="0" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">报名信息</h4>
                            </div>
                            <div class="modal-body" id="form_data" name="form_data">
                                <form id="form_active" name="form_active">
                                    <div class="form-group">
                                        <label>姓名</label>
                                        <input type="text" class="form-control" id="userName" name="userName"
                                               placeholder="请输入姓名">
                                    </div>
                                    <div class="form-group">
                                        <label>联系方式</label>
                                        <input type="text" class="form-control" id="userPhone"
                                               placeholder="请输入手机号码">
                                    </div>
                                    <div class="form-group">
                                        <label>年龄</label>
                                        <input type="text" class="form-control" id="userAge"
                                               placeholder="请输入年龄">
                                    </div>
                                    <div class="form-group">
                                        <label>参与时间段</label>
                                        <select id="activeSession" class="form-control" @change="QueryRegisterNumer">
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <blockquote class="blockquote-reverse">
                                            <footer>
                                                <span style="text-decoration: none;">剩余人数 <cite title="Source Title">{{ peopleNumber }}</cite></span>
                                            </footer>
                                        </blockquote>
                                    </div>
                                </form>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="button" id="active_push_btn" class="btn btn-primary"
                                            @click="RegisterActive()">提交报名信息
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'

    export default {
        components: {
            MainLayout
        },
        data: function () {
            return {
                id: 0,
                head: "",
                body: "",
                imgUrl: "",
                href: "",
                peopleNumber: 100,
                registerNum: 100,
                required: true,
                host:"127.0.0.1:8080"
            }
        },
        methods: {
            GetQueryString: function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
                var context = "";
                if (r != null)
                    context = r[2];
                reg = null;
                r = null;
                return context == null || context == "" || context == "undefined" ? "" : context;
            },
            ActiveRegister: function () {
                $('#active_modal').modal('show');
            },
            RegisterActive: function () {
                if (this.peopleNumber === 0) {
                    alert("报名人数已满");
                }
                else {
                    var submitUrl = "http://" + this.host + "/JoinAdd";
                    var htmlobj = $.ajax({
                        type: 'GET',
                        url: submitUrl,
                        data: {
                            userName: $("#userName").val(),
                            userAge: $("#userAge").val(),
                            activeId: this.id,
                            activeName: this.head,
                            userPhone: $("#userPhone").val(),
                            activeSession: $("#activeSession").val()
                        },
                        async: false
                    });
                    console.log(htmlobj.responseText);
                    if (htmlobj.responseText == "1")
                    {
                        alert("报名成功！");
                        window.location = "/active?" + "id="+ this.id;
                    }
                    else if (htmlobj.responseText == "-2")
                    {
                        alert("你已经报名，请勿重复报名！");
                        window.location = "/active?" + "id="+ this.id;
                    }
                    else
                    {
                        alert("报名失败，请联系微信公众号客服！");
                    }
                }
            },
            QueryRegisterNumer: function () {
                var queryRegsiterNumber = "http://" + this.host + "/QueryJoinInfoCount";
                var result = $.ajax({
                    type: 'GET',
                    url: queryRegsiterNumber,
                    data: {activeId: this.id,activeSession: $("#activeSession").val()},
                    async: false
                });
                console.log(result.responseText);
                var resultData = parseInt(result.responseText);
                this.peopleNumber = this.registerNum - resultData;
            }
        },
        mounted()
        {
            var submitUrl = "http://" + this.host +  "/QueryActiveInfoById";
            try {
                var htmlobj = $.ajax({
                    type: 'GET',
                    url: submitUrl,
                    data: {id: this.GetQueryString("id")},
                    async: false
                });
                console.log(htmlobj.responseText);
                var resultData = JSON.parse(htmlobj.responseText);
                this.id = resultData.id;
                this.body = resultData.activeBody;
                this.head = resultData.activeHead;
                this.time = resultData.activePublishDate;
                this.count = resultData.activeBrowersCount;
                this.auther = resultData.activeAuthor;
                var number = resultData.activeCount;
                for (var i = 0; i < number; i++) {
                    $('#activeSession').append("<option> " + Number(i + 1) + "</option>");
                }
                this.registerNum = resultData.activeUserCount;
                this.QueryRegisterNumer();
            }
            catch (error) {
                console.log(error);
                this.body = '<img src=\'/img/pic1.jpg\'>';
            }
            finally {

            }
        },
        updated()
        {
            $("img").attr("class", "img-responsive center-block");
        }

    }
</script>
