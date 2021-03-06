package ru.otus.handler;


import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.listener.Listener;
import ru.otus.processor.DateTimeProvider;
import ru.otus.processor.Processor;
import ru.otus.processor.TimeException;
import ru.otus.processor.TimeProcessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(message);
    }

    @Test
    @DisplayName("Тестируем TimeProcessor")
    void TimeProcessorTest() throws RuntimeException {
        var message = new Message.Builder(1L).field7("field7").build();
        var dateTimeProvider1 = new DateTimeProvider() {
            @Override
            public LocalDateTime getDate() {
                return LocalDateTime.of(2022, 3, 24, 5, 0, 1);
            }
        };
        var dateTimeProvider2 = new DateTimeProvider() {
            @Override
            public LocalDateTime getDate() {
                return LocalDateTime.of(2022, 3, 24, 5, 0, 2);
            }
        };

        var processor1 = new TimeProcessor(dateTimeProvider1);
        var processor2 = new TimeProcessor(dateTimeProvider2);

        assertThat(message).isEqualTo(processor1.process(message));

        Exception exception = assertThrows(TimeException.class, () -> {
            processor2.process(message);
        });
        var expectedMessage = "Time error: seconds are even";
        var actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}