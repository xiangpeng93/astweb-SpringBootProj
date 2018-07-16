<template>
    <main-layout>
        <div class="container">
            <div class="form-group">
                <!-- Text input-->
                <label>活动类别</label>
                <br/>
                <select id="active_type" name="active_type"
                        class="form-control" @change="chooseActiveType">
                    <option>STEAM活动</option>
                    <option>AST课程</option>
                    <option>作品展示</option>
                </select>
            </div>
            <ul style="padding:0px;">
                <li :is="item.component" :head="item.headText" :body="item.bodyText" :imgUrl="item.imgUrl" :id="item.id"
                    :hostName="item.hostName"
                    v-for="item in items"></li>
            </ul>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    import TitleCard from '../components/titleCard.vue'

    export default {
        components: {
            MainLayout,
            'TitleCard': TitleCard,
        },
        data() {
            return {
                items: [],
                host: window.location.host,
                webHost: window.location.host,
                defaultImg: "img/1.jpg"
            }
        },
        methods: {
            getFirstImgUrl: function (body) {
                var re = /src=\"([^\"]*?)\"/i;
                var arr = body.match(re);
                if (arr != undefined && arr.length > 0) {
                    console.log(arr);
                    return arr[1];
                }
                else {
                    return this.defaultImg;
                }
            },
            getActivesInfo: function () {
                try {
                    this.items = [];
                    var submitUrl = "http://" + this.host + "/QueryActivesInfo";
                    var htmlobj = $.ajax({
                        type: 'GET',
                        url: submitUrl,
                        data: {activeBegin: '0', activeCount: '5', activeTypeName: $("#active_type").val()},
                        async: false
                    });
                    var resultData = JSON.parse(htmlobj.responseText);
                    for (var i = 0; i < resultData.length; i++) {
                        this.items.push({
                            'component': 'TitleCard',
                            'id': resultData[i].id,
                            'headText': resultData[i].activeHead,
                            'bodyText': resultData[i].activeBody,
                            'imgUrl': this.getFirstImgUrl(resultData[i].activeBody),
                            'href': "http://" + this.webHost + "/active?id=" + resultData[i].id,
                            'hostName': this.host
                        });
                    }
                }
                catch (error) {
                    console.log(error);
                }
            },
            chooseActiveType: function () {
                this.getActivesInfo();
            }
        },
        mounted()
        {
            this.chooseActiveType();
        }
    }

</script>
