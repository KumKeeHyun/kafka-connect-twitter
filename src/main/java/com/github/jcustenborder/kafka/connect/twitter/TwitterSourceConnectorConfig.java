/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.twitter;

import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.types.Password;

import java.util.Map;
public class TwitterSourceConnectorConfig extends AbstractConfig {

  public static final String TWITTER_BEARER_TOKEN_CONF = "twitter.bearerToken";
  public static final String TWITTER_BEARER_TOKEN_DOC = "Twitter API Bearer token.";
  public static final String FILTER_RULE_CONF = "twitter.filter.rule";
  public static final String FILTER_RULE_DOC = "Twitter rule used in filtering.";
  public static final String EXPANSIONS_CONF = "twitter.expansions";
  public static final String EXPANSIONS_DOC = "Expand objects referenced in the payload.";
  public static final String TWEET_FIELDS_CONF = "twitter.fields.tweet";
  public static final String TWEET_FIELDS_DOC = "Fields that will be returned for tweet.";
  public static final String USER_FIELDS_CONF = "twitter.fields.user";
  public static final String USER_FIELDS_DOC = "Fields that will be returned for user.";
  public static final String PLACE_FIELDS_CONF = "twitter.fields.place";
  public static final String PLACE_FIELDS_DOC = "Fields that will be returned for place.";

  public static final String KAFKA_TWEETS_TOPIC_CONF = "kafka.tweets.topic";
  public static final String KAFKA_TWEETS_TOPIC_DOC = "Kafka topic to write the tweets to.";
  public static final String QUEUE_EMPTY_MS_CONF = "queue.empty.ms";
  public static final String QUEUE_EMPTY_MS_DOC = "The amount of time to wait if there are no records in the queue.";
  public static final String QUEUE_BATCH_SIZE_CONF = "queue.batch.size";
  public static final String QUEUE_BATCH_SIZE_DOC = "The number of records to return in a single batch.";

  public final Password bearerToken;
  public final String filterRule;
  public final String tweetFields;
  public final String expansions;
  public final String userFields;
  public final String placeFields;

  public final String topic;
  public final int queueEmptyMs;
  public final int queueBatchSize;

  public TwitterSourceConnectorConfig(Map<String, String> parsedConfig) {
    super(conf(), parsedConfig);
    this.topic = getString(KAFKA_TWEETS_TOPIC_CONF);
    this.filterRule = getString(FILTER_RULE_CONF);
    this.expansions = getString(EXPANSIONS_CONF);
    this.tweetFields = getString(TWEET_FIELDS_CONF);
    this.userFields = getString(USER_FIELDS_CONF);
    this.placeFields = getString(PLACE_FIELDS_CONF);
    this.queueBatchSize = getInt(QUEUE_BATCH_SIZE_CONF);
    this.queueEmptyMs = getInt(QUEUE_EMPTY_MS_CONF);
    this.bearerToken = getPassword(TWITTER_BEARER_TOKEN_CONF);
  }

  public static ConfigDef conf() {
    return new ConfigDef()
            .define(TWITTER_BEARER_TOKEN_CONF, Type.PASSWORD, Importance.HIGH, TWITTER_BEARER_TOKEN_DOC)
            .define(FILTER_RULE_CONF, Type.STRING, null, Importance.HIGH, FILTER_RULE_DOC)
            .define(EXPANSIONS_CONF, Type.STRING, null, Importance.MEDIUM, EXPANSIONS_DOC)
            .define(TWEET_FIELDS_CONF, Type.STRING, null, Importance.MEDIUM, TWEET_FIELDS_CONF)
            .define(USER_FIELDS_CONF, Type.STRING, null, Importance.MEDIUM, USER_FIELDS_CONF)
            .define(PLACE_FIELDS_CONF, Type.STRING, null, Importance.MEDIUM, PLACE_FIELDS_CONF)
            .define(KAFKA_TWEETS_TOPIC_CONF, Type.STRING, Importance.HIGH, KAFKA_TWEETS_TOPIC_DOC)
            .define(
                    ConfigKeyBuilder.of(QUEUE_EMPTY_MS_CONF, Type.INT)
                            .importance(Importance.LOW)
                            .documentation(QUEUE_EMPTY_MS_DOC)
                            .defaultValue(100)
                            .validator(ConfigDef.Range.atLeast(10))
                            .build()
            )
            .define(
                    ConfigKeyBuilder.of(QUEUE_BATCH_SIZE_CONF, Type.INT)
                            .importance(Importance.LOW)
                            .documentation(QUEUE_BATCH_SIZE_DOC)
                            .defaultValue(100)
                            .validator(ConfigDef.Range.atLeast(1))
                            .build()
            );
  }

}
