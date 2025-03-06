package org.example.studentprotal.dao;
//title,imageUrl,isFound,String username,studentNumber,year,
//semester
public record LostAndFoundAdminInfo(
        String title,
        String imageUrl,
        boolean isFound,
        String username,
        String studentNumber,
        String year,
        String semester
) {
}
