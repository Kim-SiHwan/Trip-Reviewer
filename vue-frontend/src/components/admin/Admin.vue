<template>
  <v-app>
    <v-container>
      <v-data-table
          v-if="memberList"
          :headers="memberHeaders"
          :items="memberList"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1"
          hide-default-footer

          @page-count="pageCount= $event">

        <template v-slot:item="memberList">
          <tr>
            <td width="500">{{memberList.item.username}}</td>
            <td>{{memberList.item.createDate}}</td>
          </tr>
        </template>
      </v-data-table>


      <v-data-table
          v-if="tagList"
          :headers="tagHeaders"
          :items="tagList"
          :items-per-page="itemsPerPage"
          :page.sync="page"
          class="elevation-1 mt-15"
          hide-default-footer

          @page-count="pageCount= $event">

        <template v-slot:item="tagList">
          <tr>
            <td width="500">{{tagList.item.tag}}</td>
            <td >
              <v-icon
                small
                color="red"
                @click="deleteTag(tagList.item.id)"
            >
              mdi-delete
            </v-icon></td>
          </tr>
        </template>
      </v-data-table>
    </v-container>
  </v-app>

</template>

<script>
export default {
  name: "Admin",
  data(){
    return{
      itemsPerPage:12,
      pageCount: 0,
      page:1,
      memberHeaders: [
        {
          text: '아이디',
          align: 'start',
          sortable: false
        },
        {
          text: '가입날짜'
        }
      ],
      tagHeaders: [
        {
          text: '태그',
          align: 'start',
        },
      ]

    }
  },
  methods:{
    deleteTag(tagId){
      this.$store.dispatch('REQUEST_DELETE_TAG',tagId);
    }
  },
  created() {
    this.$store.dispatch('REQUEST_GET_ALL_MEMBERS');
    this.$store.dispatch('REQUEST_GET_ALL_TAGS');
  },
  computed:{
    memberList(){
      return this.$store.state.adminStore.memberList;
    },
    tagList(){
      return this.$store.state.adminStore.tagList;
    }

  }
}
</script>

<style scoped>

</style>