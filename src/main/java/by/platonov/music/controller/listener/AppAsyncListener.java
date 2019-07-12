package by.platonov.music.controller.listener;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-12.
 * @version 0.0.1
 */
@Log4j2
public class AppAsyncListener implements AsyncListener {

    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        log.info("onComplete");
        String message = MessageManager.getMessage("message.email.ready");
        asyncEvent.getSuppliedResponse().getWriter().write(message);
//        asyncEvent.getAsyncContext().dispatch(PageConstant.LOGIN_PAGE);

    }

    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
//        asyncEvent.getAsyncContext().dispatch(PageConstant.VERIFICATION_PAGE);
        log.info("onWaiting");
    }

    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        log.info("on error");
    }

    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        String message = MessageManager.getMessage("message.emailsent") +
                asyncEvent.getSuppliedRequest().getAttribute(RequestConstant.EMAIL) +
                MessageManager.getMessage("message.verification");
        asyncEvent.getSuppliedResponse().getWriter().write(message);
        log.info("onStart");
    }
}
