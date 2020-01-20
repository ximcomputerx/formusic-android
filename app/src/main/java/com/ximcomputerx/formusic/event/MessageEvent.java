package com.ximcomputerx.formusic.event;

/**
 * @AUTHOR HACKER
 */
public class MessageEvent {
    public int messageId;

    public Object messageContent;

    public MessageEvent(int messageId, Object messageContent) {
        this.messageId = messageId;
        this.messageContent = messageContent;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Object getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(Object messageContent) {
        this.messageContent = messageContent;
    }
}
