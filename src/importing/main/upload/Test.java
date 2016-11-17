package importing.main.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

public class Test {
	CouchbaseEnvironment environment = DefaultCouchbaseEnvironment.builder().connectTimeout(50000).kvTimeout(25000)
			.queryTimeout(25000).disconnectTimeout(2500000).managementTimeout(25000).socketConnectTimeout(25000)
			.build();
	Cluster cluster = CouchbaseCluster.create(environment, "localhost");
	Bucket bucket = cluster.openBucket("test");
	
	
	
	public void connectAndUpload(JsonDocument doc) throws Exception {
		bucket.upsert(doc);
	}
}
