package ru.otus.listener.homework;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class MessageHistoryObject {

    private Long id;
    private Message message;
    private LocalDateTime messageTime;

    MessageHistoryObject(Long id, Message message, LocalDateTime messageTime) {
        this.id = id;
        this.message = message;
        this.messageTime = messageTime;
    }

    public static MessageHistoryObjectBuilder builder() {
        return new MessageHistoryObjectBuilder();
    }


    public Long getId() {
        return this.id;
    }

    public Message getMessage() {
        return this.message;
    }

    public LocalDateTime getMessageTime() {
        return this.messageTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MessageHistoryObject)) return false;
        final MessageHistoryObject other = (MessageHistoryObject) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$messageTime = this.getMessageTime();
        final Object other$messageTime = other.getMessageTime();
        if (this$messageTime == null ? other$messageTime != null : !this$messageTime.equals(other$messageTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MessageHistoryObject;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $messageTime = this.getMessageTime();
        result = result * PRIME + ($messageTime == null ? 43 : $messageTime.hashCode());
        return result;
    }

    public String toString() {
        return "MessageHistoryObject(id=" + this.getId() + ", message=" + this.getMessage() + ", messageTime=" + this.getMessageTime() + ")";
    }

    public static class MessageHistoryObjectBuilder {
        private Long id;
        private Message message;
        private LocalDateTime messageTime;

        MessageHistoryObjectBuilder() {
        }

        public MessageHistoryObjectBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MessageHistoryObjectBuilder message(Message message) {
            this.message = message;
            return this;
        }

        public MessageHistoryObjectBuilder messageTime(LocalDateTime messageTime) {
            this.messageTime = messageTime;
            return this;
        }

        public MessageHistoryObject build() {
            return new MessageHistoryObject(id, message, messageTime);
        }

        public String toString() {
            return "MessageHistoryObject.MessageHistoryObjectBuilder(id=" + this.id + ", message=" + this.message + ", messageTime=" + this.messageTime + ")";
        }
    }
}
