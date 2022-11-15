// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        //wx.setStorageSync("token", res.code)
        //console.log(wx.getStorageSync("token"))
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        } 
      }
    })
  },
  globalData: {
    userInfo: null,
    domain: "https://pf.rebus.work" //全局的服务器请求地址，必须包含协议头（http://或https://），根据实际情况可加上端口号（:8080）
    //domain: "http://127.0.0.1:8080/test" //测试地址
  },

  Check()
  {
    setTimeout(function()
    {
      if(wx.getStorageSync('JSESSIONID') != "") return true;
      else
      {
        wx.showToast({
          title: '缺少关键cookie，请重新登录', //弹框内容
          icon: 'none',  //弹框模式
          duration: 2000    //弹框显示时间
        });
        setTimeout(function () {
          wx.reLaunch({
            url: '/pages/index/index',
          })
        }, 1000);
      }
    },1000);
  }
})
