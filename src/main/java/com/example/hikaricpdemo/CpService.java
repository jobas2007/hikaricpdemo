package com.example.hikaricpdemo;

import com.example.hikaricpdemo.model.book.Book;
import com.example.hikaricpdemo.model.book.BookRepository;
import com.example.hikaricpdemo.model.owner.Owner;
import com.example.hikaricpdemo.model.owner.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class CpService {

    private final TransactionTemplate transactionTemplate;

    private final BookRepository bookRepository;

    private final OwnerRepository ownerRepository;

    public synchronized void aggregateOwnerData() {
        AtomicInteger i = new AtomicInteger(0);
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        log.debug("book id:{} being processed", book.getBookId());
                        if (i.getAndIncrement() == 2) {
                            throw new RuntimeException();
                        }
                        Owner owner = new Owner();
                        owner.setOwnerName(getOwnerNameRandom());
                        owner.setBookId(book.getBookId());
                        ownerRepository.save(owner);
                        log.debug("sleeping .........");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (Exception e) {
                        log.error("error occurred for book id:" + book.getBookId(), e);
                        status.setRollbackOnly();
                    }
                }
            });
        });
    }

//    @Transactional
//    public synchronized void aggregateOwnerData() {
//        AtomicInteger i = new AtomicInteger(0);
//        List<Book> books = bookRepository.findAll();
//        books.forEach(book -> {
//            try {
//                log.debug("book id:{} being processed", book.getBookId());
//                if (i.getAndIncrement() == 2) {
//                    throw new RuntimeException();
//                }
//
//                Owner owner = new Owner();
//                owner.setOwnerName(getOwnerNameRandom());
//                owner.setBookId(book.getBookId());
//                ownerRepository.save(owner);
//                log.debug("sleeping .........");
//                TimeUnit.SECONDS.sleep(5);
//            } catch (Exception e) {
//                log.error("error occurred for book id:" + book.getBookId(), e);
//            }
//        });
//    }

    private String getOwnerNameRandom() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
