package mz.org.fgh.mentoring.service;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Named;

import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.infra.UserContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 7/10/17.
 */
public class UserServiceImpl implements UserService {

    @Inject
    @Named("account")
    Retrofit accountService;

    @Inject
    EventBus eventBus;

    @Inject
    @Named("mentoring")
    Retrofit mentoringService;

    @Inject
    public UserServiceImpl() {
    }

    @Override
    public void changeUserPassword(final UserContext context) {

        UserServiceResource userServiceResource = accountService.create(UserServiceResource.class);

        userServiceResource.changePassword(context).enqueue(new Callback<UserContext>() {
            @Override
            public void onResponse(Call<UserContext> call, Response<UserContext> response) {
                eventBus.post(new MessageEvent<>(context));
            }

            @Override
            public void onFailure(Call<UserContext> call, Throwable t) {
                MessageEvent<UserContext> messageEvent = new MessageEvent<>();
                messageEvent.setError(t.getMessage());
                eventBus.post(messageEvent);
            }
        });
    }

    @Override
    public void resetPassword(UserContext context) {
        final MessageEvent<Tutor> messageEvent = new MessageEvent<>();
        UserServiceResource userServiceResource = mentoringService.create(UserServiceResource.class);

        userServiceResource.resetPassword(context).enqueue(new Callback<Tutor>() {
            @Override
            public void onResponse(Call<Tutor> call, Response<Tutor> response) {

                Tutor body = response.body();

                if (body == null) {
                    messageEvent.setError("");
                }

                messageEvent.setMessage(body);
                eventBus.post(messageEvent);
            }

            @Override
            public void onFailure(Call<Tutor> call, Throwable t) {
                messageEvent.setError(t.getMessage());
                eventBus.post(messageEvent);
            }
        });
    }
}
