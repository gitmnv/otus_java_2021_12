package ru.otus.listener.homework;

import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public class MessageHistoryObject {

    private Long id;
    private Message message;
    private List<String> field13;
    private LocalDateTime messageTime;

    MessageHistoryObject(Long id, Message message, List<String> field13, LocalDateTime messageTime) {
        this.id = id;
        this.message = message;
        this.field13 = field13;
        this.messageTime = messageTime;
        message.getField13().setData(field13);

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

    public List<String> getField13() {
        return this.field13;
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

    public void setField13(List<String> field13) {
        this.field13 = field13;
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
        final Object this$field13 = this.getField13();
        final Object other$field13 = other.getField13();
        if (this$field13 == null ? other$field13 != null : !this$field13.equals(other$field13)) return false;
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
        final Object $field13 = this.getField13();
        result = result * PRIME + ($field13 == null ? 43 : $field13.hashCode());
        final Object $messageTime = this.getMessageTime();
        result = result * PRIME + ($messageTime == null ? 43 : $messageTime.hashCode());
        return result;
    }

    public String toString() {
        return "MessageHistoryObject(id=" + this.getId() + ", message=" + this.getMessage() + ", field13=" + this.getField13() + ", messageTime=" + this.getMessageTime() + ")";
    }

    public static class MessageHistoryObjectBuilder {
        private Long id;
        private Message message;
        private List<String> field13;
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

        public MessageHistoryObjectBuilder field13(List<String> field13) {
            this.field13 = field13;
            return this;
        }

        public MessageHistoryObjectBuilder messageTime(LocalDateTime messageTime) {
            this.messageTime = messageTime;
            return this;
        }

        public MessageHistoryObject build() {
            return new MessageHistoryObject(id, message, field13, messageTime);
        }

        public String toString() {
            return "MessageHistoryObject.MessageHistoryObjectBuilder(id=" + this.id + ", message=" + this.message + ", field13=" + this.field13 + ", messageTime=" + this.messageTime + ")";
        }
    }
}
