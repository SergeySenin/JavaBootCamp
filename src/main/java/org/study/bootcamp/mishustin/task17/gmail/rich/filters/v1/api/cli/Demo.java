package org.study.bootcamp.mishustin.task17.gmail.rich.filters.v1.api.cli;

import org.study.bootcamp.mishustin.task17.gmail.rich.filters.v1.domain.model.Email;
import org.study.bootcamp.mishustin.task17.gmail.rich.filters.v1.application.service.EmailProcessor;

import java.util.*;
import java.util.function.*;

public class Demo {
    public static void main(String[] args) {

        EmailProcessor processor = new EmailProcessor();

        List<Email> inbox = Arrays.asList(
                new Email("Invoice", "Payment due tomorrow", true),
                new Email("Spam offer", "Buy now!!!", false),
                new Email("Project update", "Meeting moved to Friday", true)
        );

        Predicate<Email> importantOnly = email -> email.isImportant();

        Function<Email, String> addSignature = email -> email.getBody() + " — [Processed by Gmail]";

        Consumer<Email> printSubject = email -> System.out.println("Processed: " + email.getSubject());

        processor.processEmails(inbox, importantOnly, addSignature, printSubject);

        inbox.forEach(email -> System.out.println(email));
    }
}
