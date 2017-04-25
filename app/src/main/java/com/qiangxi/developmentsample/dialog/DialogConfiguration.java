package com.qiangxi.developmentsample.dialog;

/**
 * 作者 任强强 on 2017/4/24 11:39.
 * Dialog配置类
 */

public class DialogConfiguration {
    private String dialogTitle;
    private String dialogContent;
    private String dialogLeftText;
    private String dialogRightText;

    DialogConfiguration() {
        dialogTitle = "标题";
        dialogContent = "内容";
        dialogLeftText = "取消";
        dialogRightText = "确认";
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    private void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogContent() {
        return dialogContent;
    }

    private void setDialogContent(String dialogContent) {
        this.dialogContent = dialogContent;
    }

    public String getDialogLeftText() {
        return dialogLeftText;
    }

    private void setDialogLeftText(String dialogLeftText) {
        this.dialogLeftText = dialogLeftText;
    }

    public String getDialogRightText() {
        return dialogRightText;
    }

    private void setDialogRightText(String dialogRightText) {
        this.dialogRightText = dialogRightText;
    }

    public static class Builder {
        private DialogConfiguration mConfiguration;

        public Builder() {
            mConfiguration = new DialogConfiguration();
        }

        public Builder setDialogTitle(String dialogTitle) {
            mConfiguration.setDialogTitle(dialogTitle);
            return this;
        }

        public Builder setDialogContent(String dialogContent) {
            mConfiguration.setDialogContent(dialogContent);
            return this;
        }

        public Builder setDialogLeftText(String dialogLeftText) {
            mConfiguration.setDialogLeftText(dialogLeftText);
            return this;
        }

        public Builder setDialogRightText(String dialogRightText) {
            mConfiguration.setDialogRightText(dialogRightText);
            return this;
        }

        public DialogConfiguration build() {
            return mConfiguration;
        }
    }
}
