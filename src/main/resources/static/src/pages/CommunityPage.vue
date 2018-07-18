<!--suppress ALL -->
<template>
    <main-layout>
        <div class="container" style="padding-bottom:10px;">
            <h2 class="text-left" style="word-break: break-all; word-wrap: break-word;">{{ head }}</h2>
            <br>
            <span class="pull-left" style=" font-family: Helvetica;">
                创建时间 ： {{ time }}
            </span>
            <br>
            <span class="pull-left">发布者 ： {{ auther }}</span>
            <hr>
            <div style="white-space: pre-wrap;word-wrap: break-word;">
                <span v-html="body"></span>
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
                host: window.location.host,
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
                this.auther = resultData.communityAuthor;
            }
            catch (error) {
                console.log(error);
            }
            finally {

            }
        }
    }
</script>
