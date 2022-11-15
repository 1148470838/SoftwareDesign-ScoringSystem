// pages/show/show.js
const app = getApp();
const domain = app.globalData.domain;

Page({
  GetInfo:function(){
    var that = this;
    app.Check();
    wx.request({
      url: domain + "/ShowContest",
      header: {
        'content-type': 'application/json',
        "Cookie": wx.getStorageSync('JSESSIONID')
      },
      data: null,
      success: function (res) {
        var jsonRes = res.data.split(";");
        var arrayRes = [];
        if(res.data != "")
        {
          for (var i = 0; i <= jsonRes.length - 2; i++) 
          {
            arrayRes.push(JSON.parse(jsonRes[i]));
          }

          if(jsonRes[jsonRes.length-1].trim() === "isJudge")
          {
            that.setData({
              judge: true
            });
          }
          that.setData({
            list: arrayRes
          });
        }
        else
        {
          that.setData({
            list:[],
            none: true,
          })
        }
      },
      fail: function (res) {
        that.setData({
          list: [],
          none: true,
        })
      }
    });
  },

  Join: function (event) {
    var id = event.currentTarget.dataset.id;
    var that = this;
    if(that.data.judge == true)
    {
      wx.request({
        url: domain + '/ShowContest',
        data: "id="+id,
        method: "POST",
        header:{
          "Content-Type": "application/x-www-form-urlencoded",
          "Cookie": wx.getStorageSync('JSESSIONID')
        },
        success: function (res) {
          console.log(res.data);
          that.GetInfo();
          switch (res.data){
            case 1:
              wx.navigateTo({
                url: '../examine/request/request?id=' + id,
              });
              break;
            case 2:
              wx.showToast({
                title: '已加入该比赛', //弹框内容
                icon: 'none',  //弹框模式
                duration: 2000    //弹框显示时间
              });
              break;
            default:
              break;
          }
        },
        fail: function(res){
        },
      });
    }
    else
    {
      wx.request({
        url: domain + "/JoinContest",
        data: "id="+id,
        method: "POST",
        header:{
          "Content-Type": "application/x-www-form-urlencoded",
          "Cookie": wx.getStorageSync('JSESSIONID')
        },
        success: function (res) {
          that.GetInfo();
          switch (res.data){
            case 1:
              wx.showToast({
                title: '加入成功', //弹框内容
                icon: 'success',  //弹框模式
                duration: 2000    //弹框显示时间
              });
              break;
            case 2:
              wx.showToast({
                title: '你已加入该比赛', //弹框内容
                icon: 'none',  //弹框模式
                duration: 2000    //弹框显示时间
              });
              break;
            case 3:
              wx.showToast({
                title: '无法加入比赛', //弹框内容
                icon: 'none',  //弹框模式
                duration: 2000    //弹框显示时间
              });
              break;
            default:
              break;
          }
        },
        fail: function(res){
        },
      });
    }
  },
  
  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    none: false,
    judge: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.GetInfo();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})