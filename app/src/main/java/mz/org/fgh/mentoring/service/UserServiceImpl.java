package mz.org.fgh.mentoring.service;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Named;

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
    Retrofit retrofit;

    @Inject
    EventBus eventBus;

    @Inject
    public UserServiceImpl() {

    }

    @Override
    public void changeUserPassword(final UserContext context) {

        UserServiceResource userServiceResource = retrofit.create(UserServiceResource.class);

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
}
