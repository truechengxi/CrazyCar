﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using QFramework;

public class TimeTrialRankItem : MonoBehaviour, IController {
    public Text nameText;
    public Image avatarImage;
    public Text completeTimeText;
    public Text rankText;

    public IArchitecture GetArchitecture() {
        return CrazyCar.Interface;
    }

    public void SetContent(TimeTrialRankInfo info) {
        nameText.text = info.name;
        avatarImage.sprite = this.GetSystem<IResourceSystem>().GetAvatarResource(info.aid);
        completeTimeText.text = info.completeTime.ToString();
        rankText.text = info.rank.ToString();
    }
}
