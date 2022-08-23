import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.service.impl.ProductInfoServiceImpl;
import com.bjpowernode.utils.MD5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

public class Test01 {
    @Test
    public void md5(){
        System.out.println(MD5Util.getMD5("123456"));
    }




}
