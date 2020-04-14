package com.zjy.blog.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String indexPic; //存放地址，而非图片本身
    private String flag; //原创、转载、翻译
    private Integer views;
    private boolean thanks;
    private boolean share;  //转载声明
    private boolean comments;
    private boolean publish;
    private boolean recommend;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)  //帮助util.Date格式化
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    /**
     * 反应实体类之间的一对多，多对多关系；
     * 分为关系维护端和被维护端；
     * 有mappedBy属性的一端为被维护段，一对多的情况下是many也就是many一端；
     * 维护端可以更新外键，被维护段不可；
     * 多对多情况下依据情况来定；
     */
    @ManyToOne
    private Type type;

    @ManyToMany(cascade = CascadeType.PERSIST) //在创建博客时，如果有新创建的标签，一同写入数据库；
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;
    public List<Comment> getComms() {
        return comms;
    }

    public void setComms(List<Comment> comms) {
        this.comms = comms;
    }

    @OneToMany(mappedBy = "blog")
    private List<Comment> comms = new ArrayList<>();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIndexPic() {
        return indexPic;
    }

    public void setIndexPic(String indexPic) {
        this.indexPic = indexPic;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isThanks() {
        return thanks;
    }

    public void setThanks(boolean thanks) {
        this.thanks = thanks;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isComments() {
        return comments;
    }

    public void setComments(boolean comments) {
        this.comments = comments;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", indexPic='" + indexPic + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", thanks=" + thanks +
                ", share=" + share +
                ", comments=" + comments +
                ", publish=" + publish +
                ", recommend=" + recommend +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comms=" + comms +
                '}';
    }
}
