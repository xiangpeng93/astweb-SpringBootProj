<!--suppress ALL -->
<template>
    <main-layout>
        <div class="container" style="padding-bottom:10px;">
            <div style="min-height: 70vh">
                <h2 class="text-left" style="word-break: break-all; word-wrap: break-word;">{{ head }}</h2>
                <br>
                <span class="pull-left" style=" font-family: Helvetica;">
                创建时间 ： {{ time }}
            </span>
                <br>
                <span class="pull-left">发布者 ： {{ author }}</span>
                <hr>
                <div class="media">
                <span class="media-head">
                    <h4>{{author}}：</h4>
                </span>
                    <div class="media-body" style="white-space: pre-wrap;word-wrap: break-word;">
                        <span v-html="body"></span>
                    </div>
                </div>
                <div  class="media" v-for="item in items">
                <span class="media-head">
                    <h4>{{item.author}}：</h4>
                </span>
                    <div class="media-body" style="white-space: pre-wrap;word-wrap: break-word;">
                        <span v-html="item.body"></span>
                    </div>
                </div>
            </div>
            <div class="row" >
                <div class="container col-lg-10" style=" margin-top: 5px;"><input type="text" class="form-control" placeholder="请输入.." v-model="commentMsg" value="commentMsg"></div>
                <div class="col-lg-2" style=" margin-top: 5px;"><button class="btn btn-info pull-right" @click="publishComment()" >发布评论</button></div>
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
                body: "Test",
                author: "test",
                host: window.location.host,
                items:[],
                commentMsg:""
            }
        },
        methods: {
            publishComment:function () {
                if (this.getCookie("userId") != "") {
                    if (this.commentMsg != "") {
                        this.items.push(
                            {
                                author: this.getCookie("userId"),
                                body: this.commentMsg
                            }
                        )
                    }
                    this.commentMsg = '';
                }
                else {
                    alert("请先登录！");
                }
            },
            //获取cookie
            getCookie: function (cname) {
                try {
                    var name = cname + "=";

                    var ca = document.cookie.split(';');
                    for (var i = 0; i < ca.length; i++) {
                        var c = ca[i];
                        while (c.charAt(0) == ' ') c = c.substring(1);
                        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
                    }
                }
                catch (error) {
                    console.log(error)
                }
                return "";
            },
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
        },
        mounted()
        {
            var submitUrl = "http://" + this.host + "/QueryCommunityInfoById";
            try {
                var htmlobj = $.ajax({
                    type: 'GET',
                    url: submitUrl,
                    data: {id: this.GetQueryString("id")},
                    async: false
                });
                var resultData = JSON.parse(htmlobj.responseText);
                this.id = resultData.id;
                this.body = resultData.communityBody;
                this.head = resultData.communityHead;
                this.time = resultData.communityPublishDate;
                this.author = resultData.communityAuthor;
            }
            catch (error) {
                console.log(error);
            }
            finally {

            }
        }
    }
</script>
