// pages/login/signup/signup.js
// 获取应用实例
const app = getApp();
const domain = app.globalData.domain;

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  },

  /*注册事件*/
  create_signup: function (e) {
    
    console.log(e.detail.value);

    // 发起注册请求
    wx.request({
      url: domain + "/RegisterServlet",
      data:"username=" + e.detail.value["username"] + "&password=" + e.detail.value["password"],
      method: "POST",
      header: {
        //'content-type': 'application/json' // 默认值
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: this.getResult.bind(this) // 处理返回数据
    })

  },

  /*返回数据处理事件*/
  getResult: function (res) {
    
    console.log("......Successful Request......");
    console.log(res.data);

    // 页面交互逻辑
    if (res.data == "1") {
      wx.showToast({
        title: "注册成功",
        duration: 2000
      })
      setTimeout(function () {
        wx.navigateBack({
          delta: 1 //返回上一页（返回到传统登录页）
        })
      }, 1000)
    }
    else if(res.data == "3") {
      wx.showToast({
        title: "用户名已存在",
        icon: 'none',
        duration: 3000
      })
    }

  },
  
})