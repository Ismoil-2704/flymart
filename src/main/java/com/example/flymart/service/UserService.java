package com.example.flymart.service;

import com.example.flymart.entity.User;
import com.example.flymart.entity.enums.ServerCode;
import com.example.flymart.exeptions.DataExistsExceptions;
import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.model.ReqUserCreate;
import com.example.flymart.model.ReqUserUpdate;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.repository.RoleRepo;
import com.example.flymart.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public void create(ReqUserCreate reqUserCreate) throws DataExistsExceptions, DataNotFoundExceptions {
        Optional<User> optionalUser = userRepo.findByUserName(reqUserCreate.getUserName());
        if (optionalUser.isPresent()){
            throw new DataExistsExceptions(ServerCode.USER_EXITS.message);
        }
        User user = new User();
        userToSave(
                user, reqUserCreate.getFirstName(), reqUserCreate.getLastName(),
                reqUserCreate.getUserName(), reqUserCreate.getEmail(), reqUserCreate.getPassword(),
                reqUserCreate.getAlgorithm(), reqUserCreate.getImage(), reqUserCreate.getRoleId()
        );
    }

    public void update(ReqUserUpdate reqUserUpdate) throws DataNotFoundExceptions {
        Optional<User> optionalUser = userRepo.findById(reqUserUpdate.getId());
        if (optionalUser.isEmpty()){
            throw new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message);
        }
        User user = optionalUser.get();
        userToSave(
                user, reqUserUpdate.getFirstName(), reqUserUpdate.getLastName(),
                reqUserUpdate.getUserName(), reqUserUpdate.getEmail(), reqUserUpdate.getPassword(),
                reqUserUpdate.getAlgorithm(), reqUserUpdate.getImage(), reqUserUpdate.getRoleId()
        );
    }

    private void userToSave(User user,String firstName,String lastName,String userName,String email,String password,
                            String algorithm,String image,Long roleId) throws DataNotFoundExceptions {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setAlgorithm(algorithm);
        user.setImage(image);
        user.setRole(roleRepo.findById(roleId).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.ROLE_NOT_FOUND.message)));
        userRepo.save(user);
    }

    public void delete(Long id) throws DataNotFoundExceptions {
        User user = userRepo.findById(id).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        userRepo.delete(user);
    }

    public void updateStatus(Long id, boolean status) throws DataNotFoundExceptions {
        User user = userRepo.findById(id).orElseThrow(() -> new DataNotFoundExceptions(ServerCode.USER_NOT_FOUND.message));
        user.setStatus(status);
        userRepo.save(user);
    }

    public Object list() {
        return userRepo.findAll();
    }
}
