/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zhang.jie.api.demos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


@Controller
public class PathVariableController {



    @RequestMapping("/login/{id}")
    @ResponseBody
    public String getLogin(@PathVariable String id) {
        ArrayList<Util> utils = new ArrayList<>();
        Util util0 = new Util("1","jack","java study open at now","www.bing.com");
        Util util1 = new Util("2","lisa","C++ study open at now","www.bing.com");
        Util util2 = new Util("3","David","python study open at now","www.bing.com");
        Util util3 = new Util("4","frank","C# study open at now","www.bing.com");
        utils.add(util0);
        utils.add(util1);
        utils.add(util2);
        utils.add(util3);

        for (Util a:utils) {
            if (a.getId().equals(id)){
                return a.toString();
            }
        }
        return "false";
    }



    @PostMapping("/signup")
     @ResponseBody
     public String getPost(@RequestBody Map<String,String> parme){
         String name = parme.get("name");

         ArrayList<Util> utils = new ArrayList<>();
         Util util0 = new Util("1","jack","java study open at now","www.bing.com");
         Util util1 = new Util("2","lisa","C++ study open at now","www.bing.com");
         Util util2 = new Util("3","David","python study open at now","www.bing.com");
         Util util3 = new Util("4","frank","C# study open at now","www.bing.com");
         utils.add(util0);
         utils.add(util1);
         utils.add(util2);
         utils.add(util3);

         for (Util a:utils) {
             if (a.getName().equals(name)){
                 return a.toString();
             }
         }

        return "false";
   }


}
