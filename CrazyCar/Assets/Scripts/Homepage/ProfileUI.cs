﻿using LitJson;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using UnityEngine;
using UnityEngine.UI;
using Utils;

public class ProfileUI : MonoBehaviour {
    public Button closeBtn;
    public Image avatarImage;
    public Image vipImage;
    public InputField userNameInput;
    public Button userNameBtn;
    public InputField passwordInput;
    public Button passwordBtn;
    public Text travelTimesText;
    public Text avatarText;
    public Text mapsText;
    public Text starText;

    private void OnEnable() {
        avatarImage.sprite = GameController.manager.resourceManager.GetAvatarResource(GameController.manager.userInfo.aid);
        vipImage.gameObject.SetActiveFast(GameController.manager.userInfo.isVIP);
        userNameInput.text = GameController.manager.userInfo.name;
        passwordInput.text = PlayerPrefs.GetString(PrefKeys.password);
        starText.text = GameController.manager.userInfo.star.ToString();
        travelTimesText.text = GameController.manager.userInfo.travelTimes.ToString();
        avatarText.text = GameController.manager.userInfo.avatarNum.ToString();
        mapsText.text = GameController.manager.userInfo.mapNum.ToString(); 
    }

    private void Start() {
        closeBtn.onClick.AddListener(() => {
            UIManager.manager.HidePage(UIPageType.ProfileUI);
        });
        userNameBtn.onClick.AddListener(() => {
            if (userNameInput.text == GameController.manager.userInfo.name) {
                GameController.manager.warningAlert.ShowWithText("与原昵称一致");
            } else {

            }
        });
        passwordBtn.onClick.AddListener(() => {
            if (passwordInput.text == PlayerPrefs.GetString(PrefKeys.password)) {
                GameController.manager.warningAlert.ShowWithText("与原密码一致");
            } else if (passwordInput.text.Length < 6) {
                GameController.manager.warningAlert.ShowWithText("密码长度应大于6位");
            } else {
                StringBuilder sb = new StringBuilder();
                JsonWriter w = new JsonWriter(sb);
                w.WriteObjectStart();
                w.WritePropertyName("password");
                w.Write(passwordInput.text);
                w.WriteObjectEnd();
                Debug.Log("++++++ " + sb.ToString());
                byte[] bytes = Encoding.UTF8.GetBytes(sb.ToString());
                StartCoroutine(Util.POSTHTTP(url: NetworkController.manager.HttpBaseUrl + RequestUrl.modifyPersonalInfoUrl,
                    data: bytes, token: GameController.manager.token, 
                    succData: (data) => {
                        GameController.manager.warningAlert.ShowWithText("修改成功");
                        PlayerPrefs.SetString(PrefKeys.password, passwordInput.text);
                    },
                    code: (code) => {
                        if (code == 423) {
                            GameController.manager.warningAlert.ShowWithText("修改失败");
                        } else if (code == 404) {
                            GameController.manager.warningAlert.ShowWithText("信息有误");
                        } 
                    }));
            }
        });
    }
}