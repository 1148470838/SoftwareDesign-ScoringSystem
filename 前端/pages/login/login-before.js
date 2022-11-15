// pages/login/login-before.js
// 获取应用实例
const app = getApp()
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

  
  /*获取用户信息并跳转到微信登陆页*/
  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '获取你的昵称、头像', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        console.log(res)
        wx.setStorageSync('userInfo', res.userInfo) // 保存userInfo到Storage
        
        // 授权成功提示
        wx.showToast({
          title: "授权成功",
          duration: 500
        })
        setTimeout(function () {
          // 跳转到微信登陆页
          wx.navigateTo({
            url: "/pages/login/login-wx/login-wx",
          })
        }, 500)
      },
      fail: (res) => {
        // 授权失败提示
        wx.showModal({
          title: "获取授权失败",
          content: "必须授权你的微信昵称和头像才能继续使用小程序",
          showCancel: false
        })
      }
    })
  },

  /*按钮事件 - 导航到传统登录页*/
  goto_login: function() {
    wx.navigateTo({
      url: "/pages/login/login/login",
    })
  }

})
