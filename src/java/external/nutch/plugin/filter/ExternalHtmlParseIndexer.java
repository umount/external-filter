package external.nutch.plugin.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;

public class ExternalHtmlParseIndexer implements IndexingFilter {
  private Configuration conf;
  private String storageField;
  public static final Log LOG = LogFactory.getLog("external.nutch.plugin.filter");

  @Override
  public NutchDocument filter(NutchDocument document, String s, WebPage page) throws IndexingException {
    ByteBuffer bcontent = page.getMetadata().get(new Utf8("filtered-content"));

    if (bcontent != null) {
      String content = Bytes.toString(bcontent);

      if (storageField != null) {
        document.add(storageField, content);
      }
    }

    return document;
  }

  @Override
  public void setConf(Configuration entries) {
    this.conf = entries;

    this.storageField = getConf().get("parser.filter.storage_field", null);
  }

  @Override
  public Configuration getConf() {
    return this.conf;
  }

  @Override
  public Collection<WebPage.Field> getFields() {
    return new HashSet<WebPage.Field>();
  }
}
