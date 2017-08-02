package net.test.hasor.dataql.test;
import com.alibaba.fastjson.JSON;
import net.hasor.core.Settings;
import net.hasor.core.utils.IOUtils;
import net.hasor.core.utils.ResourcesUtils;
import net.hasor.dataql.Query;
import net.hasor.dataql.QueryResult;
import net.hasor.dataql.binder.DataQL;
import net.hasor.dataql.domain.parser.ParseException;
import net.test.hasor.dataql.AbstractTaskTest;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by yongchun.zyc on 2017/7/17.
 */
public class TestProcessMain extends AbstractTaskTest {
    @Test
    public void mainALL() throws Exception {
        this.printTaskTree("/graphql/graphql_" + 1 + ".ql");
    }
    //
    // --------------------------------------------------------------------------------------------
    private void printTaskTree(String queryResource) throws IOException, ParseException {
        InputStream inStream = ResourcesUtils.getResourceAsStream(queryResource);
        if (inStream == null) {
            return;
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("resource = " + queryResource);
        InputStreamReader reader = new InputStreamReader(inStream, Charset.forName(Settings.DefaultCharset));
        StringWriter outWriter = new StringWriter();
        IOUtils.copy(reader, outWriter);
        String buildQuery = outWriter.toString();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(buildQuery);
        //
        // - 执行 QL
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", "uid form env");
            params.put("sid", "sid form env");
            //
            DataQL gc = appContext.getInstance(DataQL.class);
            Query query = gc.createQuery(buildQuery);
            QueryResult result = query.execute(params);
            System.out.println(JSON.toJSON(result).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}