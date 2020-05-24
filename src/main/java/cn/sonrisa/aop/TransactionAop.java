/**
 * @ClassName TransactionAop
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/11 19:41
 */
package cn.sonrisa.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionAop {
}
