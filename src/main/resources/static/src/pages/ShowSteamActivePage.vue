<template>
    <main-layout>
        <div class="container-fluid" style="padding: 0px;">
        <div :is="item.component" :head="item.headText" :body="item.bodyText" :imgUrl="item.imgUrl"
             :href="item.href" v-for="item in items"></div>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    import steamTitleCard from '../components/steamTitleCard.vue'
    export default {
        components: {
            MainLayout,
            'steamTitleCard': steamTitleCard
        },
        data() {
            return {
                items: [],
                host: window.location.host,
                webHost: window.location.host,
                defaultImg: "/img/1.jpg"
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
        },
        beforeMount()
        {
            try {
                var submitUrl = "http://" + this.host + "/QueryActivesInfo";
                var htmlobj = $.ajax({
                    type: 'GET',
                    url: submitUrl,
                    data: {activeBegin: '0', activeCount: '5'},
                    async: false
                });

                var resultData = JSON.parse(htmlobj.responseText);
                for (var i = 0; i < resultData.length; i++) {
                    this.items.push({
                        'component': 'steamTitleCard',
                        'headText': resultData[i].activeHead,
                        'bodyText': resultData[i].activeBody,
                        'imgUrl': this.getFirstImgUrl(resultData[i].activeBody),
                        'href': "http://" + this.webHost + "/active?id=" + resultData[i].id
                    });
                }
            }
            catch (e) {
                console.log(e)
            }
        }

    }
</script>
