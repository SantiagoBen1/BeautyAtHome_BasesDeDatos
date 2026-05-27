package com.beautyathome.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "photo_reference")
public class PhotoReferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo_ref")
    private Integer id;

    @Column(name = "photo", length = 255)
    private String photoName;

    @Column(name = "s3_bucket_url", nullable = false, length = 500)
    private String bucketUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_review", nullable = false)
    private ReviewEntity review;

    public PhotoReferenceEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPhotoName() { return photoName; }
    public void setPhotoName(String photoName) { this.photoName = photoName; }
    public String getBucketUrl() { return bucketUrl; }
    public void setBucketUrl(String bucketUrl) { this.bucketUrl = bucketUrl; }
    public ReviewEntity getReview() { return review; }
    public void setReview(ReviewEntity review) { this.review = review; }
}
