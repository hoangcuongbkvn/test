package teamlab.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teamlab.model.dao.ActivityDao;
import teamlab.model.dao.PageDao;
import teamlab.model.dao.UserDao;
import teamlab.model.entity.Activity;
import teamlab.model.entity.Page;
import teamlab.model.entity.User;
import teamlab.model.response.UserPage;

@Service
public class PageService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PageDao pageDao;

    public List<UserPage> findUserViewedPage(String keyWord) {
        List<Page> pages = pageDao.findPageByTitle(keyWord);
        List<Activity> activitys = activityDao.findAll();
        List<User> users = userDao.findAll();

        Map<Integer, UserPage> userPageMap = new HashMap<>();
        List<UserPage> userPageList = new ArrayList<>();

        //ページごとにユーザの閲覧数
        for (int i = 0; i < pages.size(); i++) {
            boolean isFound = false;
            for (int j = 0; j < activitys.size(); j++) {
                if (pages.get(i).getId() == activitys.get(j).getPageId()) {
                    for (int k = 0; k < users.size(); k++) {
                        if (users.get(k).getId() == activitys.get(j).getUserId()) {
                            isFound = true;
                            if (userPageMap.containsKey(users.get(k).getId())) {
                                userPageMap.get(users.get(k).getId()).viewCount = userPageMap.get(users.get(k).getId()).viewCount + 1;
                            } else {
                                UserPage userPage = new UserPage();
                                userPage.pageId = pages.get(i).getId();
                                userPage.pageTitle = pages.get(i).getTitle();
                                userPage.userId = users.get(k).getId();
                                userPage.userName = users.get(k).getName();
                                userPage.viewCount = 1;
                                userPageMap.put(users.get(k).getId(), userPage);
                            }
                        }
                    }
                }
            }
            if (!isFound) {
                UserPage userPage = new UserPage();
                userPage.pageId = pages.get(i).getId();
                userPage.pageTitle = pages.get(i).getTitle();
                userPageList.add(userPage);
            }

        }

        //ユーザIDでソート
        Object[] mapkey = userPageMap.keySet().toArray();
        Arrays.sort(mapkey);
        for (Integer key : userPageMap.keySet()) {
            userPageList.add(userPageMap.get(key));
        }

        return userPageList;
    }
}
